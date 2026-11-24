# URL Shortening Service

A RESTful API for shortening long URLs, built with Java and Spring Boot.
You can create short links, look them up, update or delete them, follow them as redirects, and see how often each one was accessed.

## Features

- Create a short code for any `http`/`https` URL
- Redirect from a short code to the original URL
- Retrieve, update and delete existing short URLs
- Track how many times each short URL was accessed
- Input validation with clear error responses (`400`, `404`)
- Database schema managed with Flyway migrations

## Tech Stack

- Java 21
- Spring Boot 4 (Web MVC, Data JPA, Validation)
- PostgreSQL
- Flyway
- Maven (wrapper included)

## Getting Started

### Prerequisites

- Java 21 or newer
- A running PostgreSQL server (default: `localhost:5432`)

### 1. Clone the repository

```bash
git clone https://github.com/acxxes/url-shortening-service.git
cd url-shortening-service
```

### 2. Create the database

```sql
CREATE DATABASE example_database;
```

You don't need to create any tables. Flyway creates them on the first start.
You can use a different database name, as long as it matches `DB_NAME` in the next step.

### 3. Configure your credentials

Copy `.env.example` to `.env` in the project root and replace the placeholders with your PostgreSQL username and password:

```properties
DB_NAME=example_database
DB_USER={your-username}
DB_PASSWORD={your-password}
```

`.env` is ignored by Git, so your password stays local.

### 4. Run the application

```bash
# Linux / macOS
./mvnw spring-boot:run

# Windows (PowerShell)
.\mvnw.cmd spring-boot:run
```

The API runs on `http://localhost:8080`. Logs are also written to `logs/app.log`.

## API Reference

| Method   | Endpoint                    | Description                          | Success |
|----------|-----------------------------|--------------------------------------|---------|
| `POST`   | `/shorten`                  | Create a new short URL               | `201`   |
| `GET`    | `/shorten/{shortCode}`      | Get the original URL                 | `200`   |
| `PUT`    | `/shorten/{shortCode}`      | Update the original URL              | `200`   |
| `DELETE` | `/shorten/{shortCode}`      | Delete a short URL                   | `204`   |
| `GET`    | `/shorten/{shortCode}/stats`| Get the URL with its access count    | `200`   |
| `GET`    | `/{shortCode}`              | Redirect to the original URL         | `302`   |

Every endpoint that takes a short code returns `404 Not Found` if the code doesn't exist.
`POST` and `PUT` return `400 Bad Request` if the body is missing, empty, or not a valid `http`/`https` URL.

### Create a short URL

```http
POST /shorten
Content-Type: application/json

{
  "url": "https://www.example.com/some/long/url"
}
```

Response `201 Created`:

```json
{
  "url": "https://www.example.com/some/long/url",
  "shortCode": "aB3xK9q",
  "createdAt": "2026-11-24T16:00:00Z",
  "updatedAt": "2026-11-24T16:00:00Z"
}
```

### Get statistics

```http
GET /shorten/aB3xK9q/stats
```

Response `200 OK`:

```json
{
  "url": "https://www.example.com/some/long/url",
  "shortCode": "aB3xK9q",
  "createdAt": "2026-11-24T16:00:00Z",
  "updatedAt": "2026-11-24T16:00:00Z",
  "accessCount": 12
}
```

### Redirect

```bash
curl -i http://localhost:8080/aB3xK9q
```

Responds with `302 Found` and a `Location` header pointing to the original URL. Each redirect increases `accessCount` by one.

## How Short Codes Are Generated

Each short code is 7 random characters from the Base62 alphabet (`a-z`, `A-Z`, `0-9`), picked with `SecureRandom`.
That gives 62⁷ (about 3.5 trillion) possible codes. If a generated code already exists in the database, a new one is generated.

## Testing the API

The repository includes a few helpers in addition to plain `curl`:

- `requests.http` for the IntelliJ HTTP Client
- `http-tests/*.json` with sample request bodies (valid, empty, and wrong field)
- `http-tests/script-test.ps1`, a PowerShell script that creates a short URL and then fetches it

Example with a sample body (run from the project root):

```bash
curl -i -X POST http://localhost:8080/shorten \
  -H "Content-Type: application/json" \
  -d "@http-tests/create.json"
```

On Windows PowerShell, use `curl.exe` instead of `curl`.

## License

This project is licensed under the [MIT License](LICENSE).

## Credits

Project idea from https://roadmap.sh/projects/url-shortening-service
