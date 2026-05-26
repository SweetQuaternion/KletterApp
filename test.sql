insert into users(keycloak_id,name,bio,punkte,ascent_count,bild_url) values('347b3c17-17d6-41de-8ee6-163b876be58c','kletteradmin','Ich habe keine Bio, denn ich bin Ernst der Admin und brauche das nicht.','5','1',NULL);
insert into users(keycloak_id,name,bio,punkte,ascent_count,bild_url) values('cc78802f-0589-44f0-9fe9-2140b894ac72','mona','Fluffiges Kletterwesen, mag gerne Kaffee und Nudeln und Berge :D :D','25','8','cc78802f-0589-44f0-9fe9-2140b894ac72.gif');

insert into hallen(name,adresse,betreiber) values('DAV Testhalle :)','Lichtwiesenweg 15, 64287 Darmstadt','DAV Sektion Darmstadt-Starkenburg');

insert into wände(hallen_id,wand_nr,start_x,start_y,end_x,end_y,position) values('1','4','200','-200','500','0','indoor');
insert into wände(hallen_id,wand_nr,start_x,start_y,end_x,end_y,position) values('1','5','500','0','500','300','indoor');
insert into wände(hallen_id,wand_nr,start_x,start_y,end_x,end_y,position) values('1','1','-500','300','-500','0','outdoor');
insert into wände(hallen_id,wand_nr,start_x,start_y,end_x,end_y,position) values('1','2','-500','0','-200','-200','indoor');
insert into wände(hallen_id,wand_nr,start_x,start_y,end_x,end_y,position) values('1','3','-200','-200','200','-200','outdoor');

insert into routen(hallen_id,wand_nr,name,farbe,schwierigkeit,is_toprope,is_vorstieg,schrauber,schraubdatum,is_active) values('1','2','rote Route','rot','5.7',NULL,'true',NULL,NULL,'true');
insert into routen(hallen_id,wand_nr,name,farbe,schwierigkeit,is_toprope,is_vorstieg,schrauber,schraubdatum,is_active) values('1','2','gelbe Route','gelb','7.3',NULL,'true',NULL,NULL,'true');
insert into routen(hallen_id,wand_nr,name,farbe,schwierigkeit,is_toprope,is_vorstieg,schrauber,schraubdatum,is_active) values('1','1','blaue Route','blau','9.7',NULL,'true','Schraubmensch',NULL,'true');
insert into routen(hallen_id,wand_nr,name,farbe,schwierigkeit,is_toprope,is_vorstieg,schrauber,schraubdatum,is_active) values('1','1','grüne Route','grün','8.3',NULL,'true','Schraubmensch','2026-03-20','true');
insert into routen(hallen_id,wand_nr,name,farbe,schwierigkeit,is_toprope,is_vorstieg,schrauber,schraubdatum,is_active) values('1','2','schwere Route','schwarz','12','true','true','Schraubsi','2026-03-20','true');
insert into routen(hallen_id,wand_nr,name,farbe,schwierigkeit,is_toprope,is_vorstieg,schrauber,schraubdatum,is_active) values('1','1','Anfängerroute','orange','3.299999952316284','true',NULL,'Schraubsi','2026-03-20','true');
insert into routen(hallen_id,wand_nr,name,farbe,schwierigkeit,is_toprope,is_vorstieg,schrauber,schraubdatum,is_active) values('1','3','Nordroute weiß','weiß','5',NULL,NULL,'Schraubsi',NULL,'true');
insert into routen(hallen_id,wand_nr,name,farbe,schwierigkeit,is_toprope,is_vorstieg,schrauber,schraubdatum,is_active) values('1','3','Gepunktete Route','rot-weiß','7',NULL,'true','Schraubsi','2026-03-23','true');
insert into routen(hallen_id,wand_nr,name,farbe,schwierigkeit,is_toprope,is_vorstieg,schrauber,schraubdatum,is_active) values('1','3','Testroute','grün','4.300000190734863',NULL,'true','Schraubsi',NULL,'true');
insert into routen(hallen_id,wand_nr,name,farbe,schwierigkeit,is_toprope,is_vorstieg,schrauber,schraubdatum,is_active) values('1','3','Sonnenroute','gelb','6.300000190734863',NULL,NULL,'Schraubsi','2026-03-25','true');
insert into routen(hallen_id,wand_nr,name,farbe,schwierigkeit,is_toprope,is_vorstieg,schrauber,schraubdatum,is_active) values('1','2','Testroute','grün','8','true',NULL,'Schraubsi',NULL,'true');
insert into routen(hallen_id,wand_nr,name,farbe,schwierigkeit,is_toprope,is_vorstieg,schrauber,schraubdatum,is_active) values('1','1',NULL,NULL,NULL,'true',NULL,NULL,NULL,'true');
insert into routen(hallen_id,wand_nr,name,farbe,schwierigkeit,is_toprope,is_vorstieg,schrauber,schraubdatum,is_active) values('1','1','Fluffroute','mint','6',NULL,NULL,'Schaubfluff','2026-04-02','true');
insert into routen(hallen_id,wand_nr,name,farbe,schwierigkeit,is_toprope,is_vorstieg,schrauber,schraubdatum,is_active) values('1','2','Aufwärts','blau','4','true',NULL,'Schraubsi','2026-03-27','true');

insert into ascents(user_id,route_id,datum,style) values('cc78802f-0589-44f0-9fe9-2140b894ac72','10','2026-03-26','onsight');
insert into ascents(user_id,route_id,datum,style) values('cc78802f-0589-44f0-9fe9-2140b894ac72','3','2026-03-26','onsight');
insert into ascents(user_id,route_id,datum,style) values('347b3c17-17d6-41de-8ee6-163b876be58c','7','2026-03-27','onsight');
insert into ascents(user_id,route_id,datum,style) values('cc78802f-0589-44f0-9fe9-2140b894ac72','14','2026-04-01','onsight');
insert into ascents(user_id,route_id,datum,style) values('cc78802f-0589-44f0-9fe9-2140b894ac72','5','2026-04-27','onsight');
insert into ascents(user_id,route_id,datum,style) values('cc78802f-0589-44f0-9fe9-2140b894ac72','9','2026-04-27','onsight');
insert into ascents(user_id,route_id,datum,style) values('cc78802f-0589-44f0-9fe9-2140b894ac72','9','2026-04-27','onsight');
insert into ascents(user_id,route_id,datum,style) values('cc78802f-0589-44f0-9fe9-2140b894ac72','8','2026-04-27','onsight');
insert into ascents(user_id,route_id,datum,style) values('cc78802f-0589-44f0-9fe9-2140b894ac72','13','2026-04-27','onsight');

insert into user_routen_status(user_id,route_id,isfavorit,isprojekt,gesch_schwierigkeit,notiz) values('cc78802f-0589-44f0-9fe9-2140b894ac72','7',NULL,NULL,NULL,NULL);
insert into user_routen_status(user_id,route_id,isfavorit,isprojekt,gesch_schwierigkeit,notiz) values('cc78802f-0589-44f0-9fe9-2140b894ac72','10','true','true',NULL,NULL);
insert into user_routen_status(user_id,route_id,isfavorit,isprojekt,gesch_schwierigkeit,notiz) values('347b3c17-17d6-41de-8ee6-163b876be58c','14',NULL,NULL,NULL,NULL);
insert into user_routen_status(user_id,route_id,isfavorit,isprojekt,gesch_schwierigkeit,notiz) values('cc78802f-0589-44f0-9fe9-2140b894ac72','9','true',NULL,NULL,NULL);

insert into kommentare(user_id,route_id,datum,kommentar_text) values('cc78802f-0589-44f0-9fe9-2140b894ac72','10','2026-03-26 13:21:41.25','Wuiiii!');
insert into kommentare(user_id,route_id,datum,kommentar_text) values('347b3c17-17d6-41de-8ee6-163b876be58c','10','2026-03-26 13:57:09.978','Man kann jetzt also Kommentare schreiben. Wenn sie gegen unsere Richtlinien verstoßen, werde ich sie löschen.');
insert into kommentare(user_id,route_id,datum,kommentar_text) values('cc78802f-0589-44f0-9fe9-2140b894ac72','11','2026-04-01 07:53:17.282','Test!');
insert into kommentare(user_id,route_id,datum,kommentar_text) values('cc78802f-0589-44f0-9fe9-2140b894ac72','11','2026-04-02 10:09:53.072654','Noch ein Test');