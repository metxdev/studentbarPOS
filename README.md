# Tudengibaari POS-süsteemi projekti nõuded

## 1. Eesmärk
Luuakse veebipõhine POS-süsteem, mis võimaldab baaril hallata ja müüa erinevaid jooke (kokteilid, shotid, õlled, veinid, siidrid, toonikud, karastusjoogid jm). Süsteem peab toetama toodete täielikku elutsüklit (lisamine, muutmine, kustutamine, müügist eemaldamine / tagasikutsumine) ning dünaamilist hinnastamist vastavalt toote populaarsusele.

---

## 2. Funktsionaalsed nõuded

| Kirjeldus                                                                                                                |
|--------------------------------------------------------------------------------------------------------------------------|
| **Toote lisamine.** Kasutaja saab lisada uue toote koos kõigi vajalike väljadega.                                        |
| **Toote muutmine.** Olemasolevat toodet saab muuta (nimi, kirjeldus, hind, staatus, kategooriad, kogus).                 |
| **Toote kustutamine.** Kasutaja saab toote menüüst eemaldada.                                                            |
| **Toote väljakutsumine / peitmine.** Toote saab ajutiselt müügist eemaldada ja hiljem tagasi kutsuda.                    |
| **Dünaamiline hinnastamine.** Süsteem kohandab toote hinda automaatselt müügistatistikast arvutatud populaarsuse alusel. |
| **Müügitehing.** Kasutaja saab toodet müüa; kogus väheneb ja tehing salvestatakse ajalukku.                              |
| **Müügistatistika.** Admin saab vaadata, mitu ühikut igast tootest on müüdud kindla perioodi jooksul.                    |

---

## 3. Mittenõuded
* Rolli- ja õigushaldus implementeeritakse hilisemas etapis.
* Makselahenduse (kaart, sularaha, online-maksed) integreerimine jääb järgmisse versiooni.

---

## 4. Andmemudel

### Product

| Väli | Tüüp | Kirjeldus | Piirang |
|------|------|-----------|---------|
| `id` | Long | Primaarvõti | PK |
| `name` | String | Toote nimi | not null, unique |
| `description` | String | Lühikirjeldus / koostisosad | not null |
| `price` | BigDecimal | Hetke müügihind (€) | not null |
| `status` | Enum(`ACTIVE`, `DRAFT`, `ARCHIVED`) | Müügistaatus | not null |
| `quantity` | Integer | Laoseis / portsjonid | not null, ≥0 |
| `categories` | *Many-to-many* → `Category` | Toote kategooriad | ≥1 soovituslik |

### Category

| Väli | Tüüp | Kirjeldus |
|------|------|-----------|
| `id` | Long | Primaarvõti |
| `name` | String | Menüüsektsiooni nimi („Kokteilid“, „Õlled“…), unikaalne |

### Sale (müügiajalugu)

| Väli | Tüüp | Kirjeldus |
|------|------|-----------|
| `id` | Long | Primaarvõti |
| `product` | FK → `Product` | Müüdud toode |
| `priceAtSale` | BigDecimal | Hind tehingu hetkel |
| `timestamp` | Instant | Müügikuupäev ja -aeg |
| `quantity` | Integer | Müüdud kogus |

---

## 5. REST-API

| Verbi & tee | Kirjeldus |
|-------------|-----------|
| `POST /api/products` | Loo uus toode |
| `GET /api/products` | Kõik tooted (pageable) |
| `GET /api/products/{id}` | Üks toode detailidega |
| `PUT /api/products/{id}` | Uuenda toodet |
| `DELETE /api/products/{id}` | Kustuta toode |
| `POST /api/sales` | Registreeri müük |
| `GET /api/sales/stats?from=2025-01-01&to=2025-01-31` | Müügistatistika perioodi lõikes |
| `POST /api/categories` | Loo kategooria |
| `GET /api/categories` | Kõik kategooriad |

---

## 6. Dünaamilise hinna algoritmi visand

1. Koosta iga toote nädalane müügimaht.
2. Sorteeri tooted populaarsuse järgi (TOP → BOTTOM).
3. **TOP 20 %** → ↑ hind +5 %; **BOTTOM 20 %** → ↓ hind −5 %.
4. Tagada, et hind jääb vahemikku `[minPrice, maxPrice]`.
5. Käivita ajastatult (nt öösel kell 04:00).

---

## 7. Püsivus

* **Andmebaas:** PostgreSQL (Docker Compose maht `postgres_data`).
* **Skeemihaldus:** Flyway migratsioonid (V1__init.sql, V2__sales.sql …).
* **Spring JPA:** `spring.jpa.hibernate.ddl-auto=none`.

---

## 8. Mittefunktsionaalsed nõuded

| Valdkond | Nõue |
|----------|------|
| **Java** | OpenJDK 17+ |
| **Raamistik** | Spring Boot 3.5 |
| **Arhitektuur** | REST + PostgreSQL + Docker |
| **CI/CD** | GitHub Actions (lint, test, build image, push registry) |
| **Turvalisus** | Dev: Basic Auth; Prod: JWT / OAuth2 |
| **Lokaliseeritus** | UI eesti + inglise |
| **Logimine** | JSON-structured logid (SLF4J + Logback) |



