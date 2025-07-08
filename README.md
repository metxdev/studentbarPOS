
# Tudengibaar

Tudengibaar on dünaamiline baari POS‑lahendus, mis kohandab jookide hindu reaalajas vastavalt nende müügipopulaarsusele. Projekti eesmärk on pakkuda ülikoolisiseselt kasutatavat, kulutõhusat ja kasutajasõbralikku süsteemi, vältides kallite kommertslike POS‑platvormide rentimist.

---

## Kiire alustamine (Docker Compose)

1. Kopeeri fail `.env.example` nimega `.env` ja täida vajalikud väärtused.
2. Käivita kogu stack (taas)ehitusega:

   ```bash
   docker compose up --build
   ```
3. Backend on vaikimisi saadaval `http://localhost:8080` ja frontend `http://localhost:3000` (muuda vastavalt vajadusele).

---

## Käivitamine Maven‑projektina

1. Veendu, et sul on paigaldatud **Java 17+** ja **Maven**.
2. Ekspordi samad keskkonnamuutujad nagu `.env`‑failis.
3. Ehita ja käivita rakendus:

   ```bash
   mvn spring-boot:run
   ```

---

### Näidis `.env.example`

```env
# ---------- PostgreSQL (Docker) ----------
POSTGRES_DB=your_db
POSTGRES_USER=your_user
POSTGRES_PASSWORD=your_password
POSTGRES_PORT=5432

# ---------- Spring Boot ----------
# JDBC URL – use service name (my-postgres) inside Docker network
SPRING_DATASOURCE_URL=jdbc:postgresql://my-postgres:5432/${POSTGRES_DB}
SPRING_DATASOURCE_USERNAME=${POSTGRES_USER}
SPRING_DATASOURCE_PASSWORD=${POSTGRES_PASSWORD}

# JPA / Hibernate
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_JPA_SHOW_SQL=true
SPRING_JPA_PROPERTIES_HIBERNATE_FORMAT_SQL=true
SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT=org.hibernate.dialect.PostgreSQLDialect

# JWT
JWT_SECRET=change_me

# Front‑end CORS origin
FRONTEND_URL=http://localhost:3000

# (optional) verbose error messages while developing
SERVER_ERROR_INCLUDE_MESSAGE=always
SERVER_ERROR_INCLUDE_BINDING_ERRORS=always
```


---

## Näidiskonfiguratsioonid

**`application.yml`**

```yaml
server:
  error:
    include-message: always        # show full error text
    include-binding-errors: always # show validation errors

spring:
  datasource:
    url: ${SPRING_DATASOURCE_URL}   # matches container env
    username: ${SPRING_DATASOURCE_USERNAME}
    password: ${SPRING_DATASOURCE_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: create-drop
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
        format_sql: true
  show-sql: true
```

**`docker-compose.yml`**

```yaml
services:
  postgres:
    image: postgres:latest
    container_name: my-postgres
    environment:
      POSTGRES_DB: ${POSTGRES_DB}
      POSTGRES_USER: ${POSTGRES_USER}
      POSTGRES_PASSWORD: ${POSTGRES_PASSWORD}
    ports:
      - "${POSTGRES_PORT}:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

volumes:
  postgres_data:
```



Copyright © 2025 [Kristofer Metusala]. All rights reserved.
