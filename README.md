# 🍽️ Ristorante – Backend (Spring Boot)

API REST per la gestione del menù di un ristorante (piatti + ingredienti) usata dal frontend Angular.  
L'applicazione, nata come esercizio didattico, è stata evoluta in chiave professionale con:

- DTO per non esporre le entità verso l’esterno  
- Mapper per conversione DTO ⇄ Entity  
- CORS centralizzato  
- Gestione eccezioni con risposte JSON uniformi (`ApiError`)  
- Seed dati automatico opzionale

---

## 🚀 Deploy online (Render)

Backend LIVE:  
👉 **https://ristorante-backend-ka1l.onrender.com**

Endpoint base:  
https://ristorante-backend-ka1l.onrender.com/api/piatti

## ⚡ Cold start Render + Keep Alive (Ping)

Nel free tier di Render il backend può andare in **sleep** dopo un periodo di inattività.
Questo comporta che la **prima chiamata** alle API può essere lenta (cold start).

✅ Per migliorare l’esperienza utente è stato introdotto:

- endpoint leggero:  
  **GET /ping** → restituisce `"ok"`
- monitor esterno (UptimeRobot) che richiama periodicamente `/ping` per mantenere il servizio attivo

Endpoint ping:
👉 https://ristorante-backend-8awh.onrender.com/ping

## 🛠 Stack Tecnologico

- Java 21  
- Spring Boot 3.5  
- Spring Data JPA (Hibernate)  
- MySQL 8  
- Lombok  
- Maven  

---

## 📦 Requisiti

- **Java 21**
- **Maven 3.9+**
- **MySQL 8** con database creato a mano:

```sql
CREATE DATABASE ristorante
CHARACTER SET utf8mb4
COLLATE utf8mb4_0900_ai_ci;
Opzionale: Postman o un qualsiasi HTTP client.

🔐 Configurazione (variabili d’ambiente)
Il progetto non contiene credenziali hardcodate.
application.properties utilizza env vars:

properties

spring.datasource.url=${DB_URL:jdbc:mysql://localhost:3306/ristorante?useSSL=false&serverTimezone=UTC}
spring.datasource.username=${DB_USER:root}
spring.datasource.password=${DB_PASS} <--- qui devi inserire la password del tuo database

app.cors.allowed-origins=${APP_CORS_ORIGINS:http://localhost:4200,http://localhost:5173}
Variabili richieste
DB_URL (es. jdbc:mysql://localhost:3306/ristorante?useSSL=false&serverTimezone=UTC)

DB_USER

DB_PASS

APP_CORS_ORIGINS (origini FE consentite, separate da virgola)

Esempi di export
PowerShell (Windows)

$env:DB_URL="jdbc:mysql://localhost:3306/ristorante?useSSL=false&serverTimezone=UTC"
$env:DB_USER="root"
$env:DB_PASS=""
$env:APP_CORS_ORIGINS="http://localhost:4200"
macOS / Linux

export DB_URL="jdbc:mysql://localhost:3306/ristorante?useSSL=false&serverTimezone=UTC"
export DB_USER="root"
export DB_PASS=""
export APP_CORS_ORIGINS="http://localhost:4200"

▶️ Avvio in locale

mvn spring-boot:run
Oppure:

mvn -DskipTests package
java -jar target/*.jar
🌱 Seed dati
Nella classe DataInitializer (package com.example.config):

Popola il DB solo se vuoto

Inserisce 17 piatti + relativi ingredienti

Se non vedi i dati:

verifica env vars del DB

verifica eventuali profili

controlla i log di avvio

📡 Endpoints Principali
Base path: /api/piatti

Metodo	Endpoint	Descrizione
GET	/api/piatti	Lista completa (con ingredienti)
GET	/api/piatti/ordered	Lista ordinata per il form “conto”
GET	/api/piatti/{id}	Dettaglio
GET	/api/piatti/categoria/{categoria}	Filtro categoria (case-insensitive)
POST	/api/piatti	Crea nuovo piatto
DELETE	/api/piatti/{id}	Elimina

Esempio POST
json

{
  "nome": "Cacio e Pepe",
  "categoria": "Primi",
  "prezzo": 9.00,
  "immagine": "cacio_e_pepe.jpg",
  "ingredienti": ["Spaghetti", "Pecorino Romano", "Pepe"]
}
❗ Validazione ed Errori (ApiError)
Esempio risposta:

json

{
  "timestamp": "2025-12-03 09:30:56",
  "status": 404,
  "error": "Not Found",
  "message": "Piatto non trovato: id=60",
  "path": "/api/piatti/60",
  "details": null
}
Regole:
404 risorsa non trovata

400 validazione fallita

500 errore non gestito

🌍 CORS
Whitelist configurata in CorsConfig.

Legge:

APP_CORS_ORIGINS
Per Angular locale:

http://localhost:4200
📝 Note
spring.jpa.hibernate.ddl-auto=update consigliato solo in sviluppo.

Per produzione → validate o migrazioni con Flyway.

Java 21 richiesto.

📌 Note finali
Questo progetto fa parte di un percorso di formazione full-stack Java ed è pensato come:

dimostrazione di best practice Spring Boot
dimostrazione di utilizzo di Angular
progetto portfolio-ready, consultabile online

👨‍💻 Autore: Edoardo Mattei
📅 Anno: 2025
