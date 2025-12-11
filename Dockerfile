# ---------- BUILD STAGE ----------
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /workspace

COPY pom.xml .
# Pre-carica le dipendenze per sfruttare la cache
RUN mvn -q -e -DskipTests dependency:go-offline

COPY src ./src
RUN mvn -q -e -DskipTests package

# ---------- RUN STAGE ----------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Usiamo il profilo H2 "demo" su Render
ENV SPRING_PROFILES_ACTIVE=demo
ENV JAVA_OPTS=""
# Aiuta a non sforare la RAM nei piani free
ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75"

# Copia il jar dallo stage di build
COPY --from=build /workspace/target/*.jar /app/app.jar

# Render espone la porta tramite $PORT
CMD ["sh","-c","java $JAVA_OPTS -Dserver.port=$PORT -jar /app/app.jar"]
