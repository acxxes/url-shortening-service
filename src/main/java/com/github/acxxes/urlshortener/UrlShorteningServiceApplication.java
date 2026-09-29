package com.github.acxxes.urlshortener;

import com.github.acxxes.urlshortener.entity.Url;
import com.github.acxxes.urlshortener.repository.UrlRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UrlShorteningServiceApplication {

    //TODO
    // - learn postgreSQL, establish API with the 5 queries behind HTTP
	/*
	HTTP request  →  Controller  →  Service  →  Repository  →  SQL query  →  PostgreSQL
                                                                         	 │
	HTTP response ←  status code  ←  decision ←  result (rows / row count) ←─┘

	Database says	                        API returns
    1 row found / 1 row affected	        200, 201, or 204
    0 rows found / 0 rows affected	        404 Not Found
    Constraint error (duplicate, null)	    400 or 409, or retry (for duplicate codes)
    */

    // CASE HANDLING

    /*
     * ============================================================================
     * The API has to handle each of these cases.
     * ============================================================================
     *
     * EXPERIMENT                       | WHAT YOU'LL SEE                                  | PG CODE | LATER IN SPRING
     * ---------------------------------+--------------------------------------------------+---------+----------------------------------------------
     * Insert a duplicate unique value  | duplicate key value violates unique constraint   | 23505   | DataIntegrityViolationException
     *                                  | "books_isbn_key"                                 |         | -> retry with a new code
     * ---------------------------------+--------------------------------------------------+---------+----------------------------------------------
     * Insert without a NOT NULL column | null value in column "title" ... violates        | 23502   | Should never happen if validation
     * that has no default              | not-null constraint                              |         | (Phase 7) works first
     * (leave out title)                |                                                  |         |
     * ---------------------------------+--------------------------------------------------+---------+----------------------------------------------
     * Insert an id into a              | cannot insert a non-DEFAULT value into           | 428C9   | A reason not to set IDs yourself
     * GENERATED ALWAYS column          | column "id"                                      |         |
     * ---------------------------------+--------------------------------------------------+---------+----------------------------------------------
     * UPDATE / DELETE a missing value  | No error, 0 rows affected                        | -       | 404
     * ---------------------------------+--------------------------------------------------+---------+----------------------------------------------
     * SELECT a missing value           | No error, empty result                           | -       | Empty Optional -> 404
     * ============================================================================
     */

    // CHEAT SHEET

    /*
     * Endpoint                  | SQL statement                            | Success signal        | Failure signal
     * --------------------------+------------------------------------------+-----------------------+----------------------------------
     * POST /shorten             | INSERT ... RETURNING                     | Row returned -> 201   | Unique violation on code -> retry
     * GET /shorten/{code}       | SELECT ... WHERE + increment             | 1 row -> 200          | 0 rows -> 404
     * PUT /shorten/{code}       | UPDATE ... SET ..., updated_at ... WHERE | 1 row affected -> 200 | 0 rows -> 404
     * DELETE /shorten/{code}    | DELETE ... WHERE                         | 1 row affected -> 204 | 0 rows -> 404
     * GET /shorten/{code}/stats | SELECT including the counter             | 1 row -> 200          | 0 rows -> 404
     */

    /*
     * Request flow through the layers:
     *
     *   HTTP request (JSON)
     *         │
     *         ▼
     *   ┌──────────────┐   speaks HTTP: paths, status codes, JSON
     *   │  Controller  │   works with DTOs
     *   └──────┬───────┘
     *          ▼
     *   ┌──────────────┐   speaks "business": rules, decisions, exceptions
     *   │   Service    │   converts DTO ⇄ Entity
     *   └──────┬───────┘
     *          ▼
     *   ┌──────────────┐   speaks "database": save, find, delete
     *   │  Repository  │   works with Entities
     *   └──────┬───────┘
     *          ▼
     *      PostgreSQL
     *
        1. Separation of concerns. Each class has one job. When a status code is wrong, you look in the controller.
           When a rule is wrong, you look in the service.
        2. Testability. You can test the service with a fake repository (Phase 9) without a database or HTTP.
        3. Change isolation. You can rename a DB column without breaking your API contract, or change the API without touching the DB.
     */

    /*
    Which status code?	                Controller
    Read {code} from the URL	        Controller (@PathVariable)
    Read JSON body	                    Controller (@RequestBody)
    Validate input format (Phase 7)	    DTO annotations + @Valid in the controller
    "Code doesn't exist → error"	    Service (throws an exception)
    Generate a random code	            Service (or a small helper class it uses)
    Increment access count	            Service → Repository
    SQL / queries	                    Repository
    Entity ⇄ DTO conversion	        Service (using the DTO's factory method)
    Turn exceptions into JSON errors	@RestControllerAdvice (Phase 7)
     */

    /*
    Java object (Entity)  <-- Hibernate translates -->  table row
    Book.title                                           books.title
    repository.save(book)          -->                   INSERT / UPDATE
    repository.findByIsbn(...)     -->                   SELECT ... WHERE isbn = ?
     */

    public static void main(String[] args) {
        SpringApplication.run(UrlShorteningServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner demo(UrlRepository repository) {
        return args -> {
            Url saved = repository.save(new Url("https://iom-arch-optimizer-web.pages.dev/#/welcome", "optimizer"));
            System.out.println("Saved with id: " + saved.getId());

            repository.findByShortCode("optimizer")
                    .ifPresent(url -> System.out.println("Url: " + url.getUrl() + ", created " + url.getCreatedAt()));
        };
    }

}
