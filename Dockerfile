# ---------- BUILD STAGE ----------
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY pom.xml .
# Scarico le dipendenze prima (cache più efficace)
RUN mvn -q -e -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -q -e -DskipTests package

# ---------- RUN STAGE ----------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Render fornisce la porta in 
ENV SPRING_PROFILES_ACTIVE=demo
ENV JAVA_OPTS=""
# (opzionale) evita OOM sui free dyno
ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75"

# Copia il jar compilato
COPY --from=build /workspace/target/*.jar /app/app.jar

# Avvio: forza server.port a  per sicurezza
CMD ["sh","-c","java  -Dserver.port= -jar /app/app.jar"]
@"
# ---------- BUILD STAGE ----------
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY pom.xml .
# scarico dipendenze per sfruttare la cache
RUN mvn -q -e -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -q -e -DskipTests package

# ---------- RUN STAGE ----------
FROM eclipse-temurin:21-jre
WORKDIR /app

# useremo H2 (profilo demo) su Render
ENV SPRING_PROFILES_ACTIVE=demo
ENV JAVA_OPTS=""
# aiuta a non sforare la RAM dei piani free
ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75"

# copia il jar costruito nello stage build
COPY --from=build /workspace/target/*.jar /app/app.jar

# Render passa la porta in 
CMD ["sh","-c","java  -Dserver.port= -jar /app/app.jar"]
