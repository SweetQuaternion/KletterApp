ALTER TABLE wände ADD COLUMN id INT GENERATED ALWAYS AS IDENTITY;
ALTER TABLE wände ADD CONSTRAINT wände_hallen_id_wand_nr_unique UNIQUE (hallen_id, wand_nr);
ALTER TABLE routen ADD COLUMN wand_id INT;

UPDATE routen r
SET wand_id = w.id
FROM wände w
WHERE r.hallen_id = w.hallen_id
AND r.wand_nr = w.wand_nr;
ALTER TABLE routen ALTER COLUMN wand_id SET NOT NULL;
ALTER TABLE routen DROP COLUMN hallen_id;
ALTER TABLE routen DROP COLUMN wand_nr;
ALTER TABLE wände DROP CONSTRAINT wände_pkey;
ALTER TABLE wände ADD PRIMARY KEY (id);
ALTER TABLE routen ADD CONSTRAINT routen_wand_id_fkey FOREIGN KEY (wand_id) REFERENCES wände(id) ON DELETE CASCADE;