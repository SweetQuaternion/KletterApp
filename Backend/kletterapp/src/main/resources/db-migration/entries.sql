INSERT INTO hallen (name, adresse, betreiber) VALUES
('DAV Kletterzentrum Darmstadt', 'Lichtwiesenweg 15, 64287 Darmstadt', 'DAV Sektion Darmstadt-Starkenburg'),
('DAV Sandsteinbruch Heubach', 'Wilhelm-Leuschner-Straße 250, 64823 Groß-Umstadt', 'DAV Sektion Darmstadt-Starkenburg'),
('DAV Kletterzentrum Frankfurt / Main', 'Homburger Landstraße 283, 60433 Frankfurt am Main', 'DAV Sektion Frankfurt/Main'),
('DAV Kletterzentrum Mannheim', 'Abraham-Lincoln-Allee 7, 68309 Mannheim', 'DAV Sektion Mannheim')

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
(1, 'Dreamteam', 8.3),
(1, 'Körnung', 9.7),
(1, '3 Sterne Quergang', 5.7),
(1, 'Bahnhof Heubach', 5.7),
(1, 'Abstellgleis', 7.3),
(1, 'Teamwork', 7.3),
(1, 'Ufbasse', 7.0),
(1, 'Heinzelmännchen', 7.3),
(1, 'Nachthemd', 7.0),
(1, 'Projekt', NULL),
(1, 'Ju.did.it', 6.7),
(1, 'Sandomasso', 7.7),
(1, 'Elektra', 8.7)

-- INSERT INTO routen (wand_id, name, schwierigkeit) VALUES
-- (2, 'Steinbeißer', 7),
-- (2, 'Kleine 20', 6.3),
-- (2, 'Vari-Yang-te', 8.3),
-- (2, 'Ying & Yang', 7.7),
-- (2, 'Italia 90', 6),
-- (2, 'Babyboomer 24', 7),
-- (2, 'Konflikt', 9),
-- (2, 'Konflikt linke Kante / Projekt', NULL),
-- (2, 'Dinettissima', 8.7)

-- INSERT INTO routen (wand_id, name, schwierigkeit) VALUES
-- (3, 'Savoir vivre', 8),
-- (3, 'Traum vom Fliegen', 7.7),
-- (3, 'Tresor', 8),
-- (3, 'Verzicht', 5.7),
-- (3, 'Frag den Jürgen', 7.3),
-- (3, 'Seitenwechsel', 6.3),
-- (3, 'Lustgeschäft', 8.7),
-- (3, 'Mädchenboulder', 7.3),
-- (3, 'Mit alles und scharf', 7.3),
-- (3, 'Rot-Grün-Schwäche', 7.3),
-- (3, 'Sophistenschleuder', 7.7)
