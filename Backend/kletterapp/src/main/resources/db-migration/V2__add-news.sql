CREATE TABLE IF NOT EXISTS news(
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    hallen_id INT,
    autor text NOT NULL,
    datum timestamp NOT NULL,
    titel text NOT NULL,
    inhalt text NOT NULL,
    FOREIGN KEY (hallen_id) REFERENCES hallen(id) ON DELETE CASCADE,
    FOREIGN KEY (autor) REFERENCES users(keycloak_id) ON DELETE CASCADE
);