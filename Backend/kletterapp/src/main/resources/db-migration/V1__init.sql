CREATE DATABASE "kletterapp-db";
GRANT ALL PRIVILEGES ON DATABASE "kletterapp-db" TO kletteruser;
CREATE DATABASE "keycloak-db";
GRANT ALL PRIVILEGES ON DATABASE "keycloak-db" TO kletteruser;

\c "kletterapp-db"

CREATE TABLE IF NOT EXISTS hallen(
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name text NOT NULL,
    adresse text NOT NULL,
    betreiber text
);

CREATE EXTENSION IF NOT EXISTS pg_trgm;

SELECT set_limit(0.2);

CREATE INDEX idx_hallen_search_trgm
ON hallen
USING gin ((name || ' ' || adresse) gin_trgm_ops);

CREATE INDEX idx_hallen_name_prefix
ON hallen (name text_pattern_ops);

CREATE TABLE IF NOT EXISTS wände(
    hallen_id INT NOT NULL,
    wand_nr INT NOT NULL,
    name text,
    start_x FLOAT not null,
    start_y FLOAT not null,
    end_x FLOAT not null,
    end_y FLOAT not null,
    position text,
    FOREIGN KEY (hallen_id) REFERENCES hallen(id) ON DELETE CASCADE,
    PRIMARY KEY (hallen_id, wand_nr)
);

CREATE TABLE IF NOT EXISTS routen(
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    hallen_id INT NOT NULL,
    wand_nr INT NOT NULL,
    name text,
    farbe text,
    schwierigkeit float,
    is_toprope boolean DEFAULT false,
    is_vorstieg boolean DEFAULT true,
    schrauber text,
    schraubdatum date,
    is_active boolean DEFAULT true,
    beschreibung text,
    FOREIGN KEY (hallen_id, wand_nr) REFERENCES wände(hallen_id, wand_nr) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS users(
    keycloak_id text PRIMARY KEY,
    name text NOT NULL,
    bild_url text,
    bio text,
    punkte int default 0,
    ascent_count int default 0
);

CREATE TABLE IF NOT EXISTS ascents(
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    user_id text NOT NULL,
    route_id INT NOT NULL,
    datum date NOT NULL,
    style VARCHAR(50),
    -- sicherung VARCHAR(50),
    FOREIGN KEY (user_id) REFERENCES users(keycloak_id) ON DELETE CASCADE,
    FOREIGN KEY (route_id) REFERENCES routen(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS user_routen_status(
    user_id text NOT NULL,
    route_id INT NOT NULL,
    isFavorit boolean DEFAULT false,
    isProjekt boolean DEFAULT false,
    gesch_schwierigkeit FLOAT,
    notiz text,
    FOREIGN KEY (user_id) REFERENCES users(keycloak_id) ON DELETE CASCADE,
    FOREIGN KEY (route_id) REFERENCES routen(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS kommentare(
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    user_id text NOT NULL,
    route_id INT NOT NULL,
    datum timestamp NOT NULL,
    kommentar_text text NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(keycloak_id) ON DELETE CASCADE,
    FOREIGN KEY (route_id) REFERENCES routen(id) ON DELETE CASCADE
);

