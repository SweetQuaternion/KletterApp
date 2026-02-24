INSERT INTO hallen (name, adresse) VALUES
('DAV Kletterzentrum Darmstadt', 'Lichtwiesenweg 15, 64287 Darmstadt'),
('DAV-Sandsteinbruch Heubach', 'Wilhelm-Leuschner-Straße 250, 64823 Groß-Umstadt')


INSERT INTO wände (hallen_id, wand_nr, sektor) VALUES
(2, 1, 'Bahnhof Heubach'),
(2, 2, 'Italia 90'),
(2, 3, 'Verzicht'),
(2, 4, 'Es schneit'),
(2, 5, 'Ab durch die Mitte'),
(2, 6, 'Geht'),
(2, 7, 'Easy going'),
(2, 8, 'links oben')

INSERT INTO routen (wand_id, name, schwierigkeit) VALUES
(1, 'Dreamteam', '8+'),
(1, 'Körnung', '10-'),
(1, '3 Sterne Quergang', '6-'),
(1, 'Bahnhof Heubach', '6-'),
(1, 'Abstellgleis', '7+'),
(1, 'Teamwork', '7+'),
(1, 'Ufbasse', '7'),
(1, 'Heinzelmännchen', '7+'),
(1, 'Nachthemd', '7'),
(1, 'Projekt', ''),
(1, 'Ju.did.it', '7-'),
(1, 'Sandomasso', '8-'),
(1, 'Elektra', '9-')

INSERT INTO routen (wand_id, name, schwierigkeit) VALUES
(2, 'Steinbeißer', '7'),
(2, 'Kleine 20', '6+'),
(2, 'Vari-Yang-te', '8+'),
(2, 'Ying & Yang', '8-'),
(2, 'Italia 90', '6'),
(2, 'Babyboomer 24', '7'),
(2, 'Konflikt', '9'),
(2, 'Konflikt linke Kante / Projekt', NULL),
(2, 'Dinettissima', '9-')

INSERT INTO routen (wand_id, name, schwierigkeit) VALUES
(3, 'Savoir vivre', '8'),
(3, 'Traum vom Fliegen', '8-'),
(3, 'Tresor', '8'),
(3, 'Verzicht', '6-'),
(3, 'Frag den Jürgen', '7+'),
(3, 'Seitenwechsel', '6+'),
(3, 'Lustgeschäft', '9-'),
(3, 'Mädchenboulder', '7+'),
(3, 'Mit alles und scharf', '7+'),
(3, 'Rot-Grün-Schwäche', '7+'),
(3, 'Sophistenschleuder', '8-')



INSERT INTO users (name, email, passwort_hash) VALUES
('Mona', 'mona-philine.komp@dachpc.com', 'hash1')