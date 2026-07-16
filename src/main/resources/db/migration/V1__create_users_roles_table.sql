CREATE TABLE "user" (
                        id       SERIAL        PRIMARY KEY,
                        username VARCHAR(255)  NOT NULL UNIQUE,
                        password VARCHAR(255)  NOT NULL
);

CREATE TABLE role (
                      id      SERIAL       PRIMARY KEY,
                      user_id INT          NOT NULL REFERENCES "user"(id) ON DELETE CASCADE,
                      role    VARCHAR(255) NOT NULL
);
