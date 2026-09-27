package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

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


    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
