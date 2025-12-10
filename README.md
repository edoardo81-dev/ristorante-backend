Ristorante – Backend (Spring Boot)

API REST per la gestione del menù di un ristorante (piatti + ingredienti) usata dal frontend Angular.
l'applicazione nata come esercizio didattico è stata evoluta in chiave più professionale con dto per non esporre le entità 
verso l'esterno, mapper per convertire dto in entità e vicevera, cors centralizzato e gestione delle eccezioni separate
con risposte JSON uniformi

Stack:

Java 21, Spring Boot 3.5

Spring Data JPA (Hibernate)

MySQL 8

Lombok

Maven

Requisiti:

Java 21

Maven 3.9+

MySQL 8 con database ad es. ristorante creato a mano:

CREATE DATABASE ristorante CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;


Opzionale: Postman/HTTP client per test

Configurazione (variabili d’ambiente):

L’app non contiene credenziali hardcodate. In application.properties sono lette da env vars:

spring.datasource.url=${DB_URL:jdbc:mysql://localhost:3306/ristorante?useSSL=false&serverTimezone=UTC}
spring.datasource.username=${DB_USER:root}
spring.datasource.password=${DB_PASS}
app.cors.allowed-origins=${APP_CORS_ORIGINS:http://localhost:4200,http://localhost:5173}


Imposta quindi:

DB_URL (es. jdbc:mysql://localhost:3306/ristorante?useSSL=false&serverTimezone=UTC)

DB_USER (es. root)

DB_PASS (obbligatoria, è letta come ${DB_PASS} nel file)

APP_CORS_ORIGINS (origini FE consentite, separate da virgola)

Esempi di export:

Windows PowerShell

$env:DB_URL="jdbc:mysql://localhost:3306/ristorante?useSSL=false&serverTimezone=UTC"
$env:DB_USER="root"
$env:DB_PASS="<la-tua-password-MySQL>"
$env:APP_CORS_ORIGINS="http://localhost:4200"


macOS/Linux (bash/zsh)

export DB_URL="jdbc:mysql://localhost:3306/ristorante?useSSL=false&serverTimezone=UTC"
export DB_USER="root"
export DB_PASS="<la-tua-password-MySQL>"
export APP_CORS_ORIGINS="http://localhost:4200"

Avvio in locale:
mvn spring-boot:run


Oppure build JAR:

mvn -DskipTests package
java -jar target/*.jar

Seed dati:

È presente un DataInitializer (in com.example.config). Se attivo, popola il DB solo se vuoto con 17 piatti (Primi/Secondi/Dolci/Bevande) e relativi ingredienti. In caso non veda dati:

verifica le env vars del DB,

verifica che il profilo/condizioni del DataInitializer (se annotato con @Profile) siano attive,

controlla i log di avvio.

Endpoints principali:

Base path: /api/piatti

GET /api/piatti – lista completa (con ingredienti)

GET /api/piatti/ordered – lista ordinata per il form “conto”
(ordine categorie: Primi → Secondi → Dolci → Bevande, poi nome asc)

GET /api/piatti/{id} – dettaglio per id

GET /api/piatti/categoria/{categoria} – filtra per categoria (case-insensitive)

POST /api/piatti – crea nuovo piatto
Esempio body

{
  "nome": "Cacio e Pepe",
  "categoria": "Primi",
  "prezzo": 9.00,
  "immagine": "cacio_e_pepe.jpg",
  "ingredienti": ["Spaghetti","Pecorino Romano","Pepe"]
}


DELETE /api/piatti/{id} – elimina per id

Validazione e errori:

Gli errori sono normalizzati in ApiError:

{
  "timestamp": "2025-12-03 09:30:56",
  "status": 404,
  "error": "Not Found",
  "message": "Piatto non trovato: id=60",
  "path": "/api/piatti/60",
  "details": null
}


Regole principali:

404 Not Found per risorse inesistenti

400 Bad Request per validazione (@Valid) o parametri errati

500 Internal Server Error per errori non gestiti

CORS:

La whitelist è configurata in CorsConfig, legge APP_CORS_ORIGINS.
Esempio per Angular:

APP_CORS_ORIGINS=http://localhost:4200

Note:

spring.jpa.hibernate.ddl-auto=update è comodo in sviluppo; per produzione valuta validate o migrazioni (Flyway).

Java 21 è richiesto.

Come aggiungere il file e fare push

Apri PowerShell nella cartella del progetto (dove c’è il pom.xml) e incolla:

@"
# (incolla qui TUTTO il contenuto del README sopra)
"@ | Out-File -Encoding UTF8 README.md

git add README.md
git commit -m "Add README with setup, env vars and endpoints"
git push
