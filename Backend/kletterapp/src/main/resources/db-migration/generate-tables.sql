CREATE TABLE hallen(
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name text NOT NULL,
    adresse text NOT NULL,
    betreiber text NOT NULL DEFAULT 'Unbekannt'
);

CREATE TABLE wände(
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    hallen_id INT NOT NULL,
    wand_nr INT,
    sektor text,
    FOREIGN KEY (hallen_id) REFERENCES hallen(id)
);

CREATE TABLE routen(
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    wand_id INT NOT NULL,
    name text,
    farbe text,
    schwierigkeit float,
    is_toprope boolean DEFAULT false,
    is_vorstieg boolean DEFAULT true,
    schrauber text,
    schraubdatum timestamp,
    is_active boolean DEFAULT true,
    beschreibung text,
    FOREIGN KEY (wand_id) REFERENCES wände(id)
);

CREATE TABLE users(
    keycloak_id text PRIMARY KEY,
    name text NOT NULL,
    bild_url text
);

CREATE TYPE style_enum AS ENUM ('onsight', 'flash', 'redpoint', 'pinkpoint', 'toprope', 'hangdog', 'attempt');
CREATE TYPE sicherung_enum AS ENUM ('vorstieg', 'toprope', 'solo');

CREATE TABLE ascents(
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    user_id text NOT NULL,
    route_id INT NOT NULL,
    datum timestamp NOT NULL,
    style style_enum,
    sicherung sicherung_enum,
    FOREIGN KEY (user_id) REFERENCES users(keycloak_id),
    FOREIGN KEY (route_id) REFERENCES routen(id)
);

CREATE TABLE user_routen_status(
    user_id text NOT NULL,
    route_id INT NOT NULL,
    isFavorit boolean DEFAULT false,
    isProjekt boolean DEFAULT false,
    gesch_schwierigkeit FLOAT,
    notiz text,
    FOREIGN KEY (user_id) REFERENCES users(keycloak_id),
    FOREIGN KEY (route_id) REFERENCES routen(id)
);

CREATE TABLE kommentare(
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    user_id text NOT NULL,
    route_id INT NOT NULL,
    datum timestamp NOT NULL,
    kommentar_text text NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(keycloak_id),
    FOREIGN KEY (route_id) REFERENCES routen(id)
);