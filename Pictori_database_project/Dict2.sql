insert into words values (18,"bus",   26,  2);
insert into words values (40,"busz",   18,  1);
insert into languages values(1, "Magyar");
insert into languages values(2, "Angol");
insert into languages values(3, "N�met");
insert into words values (25,"aut�busz", 18, 1);



select * from languages;

select * from words;


//�j szavak felvitele
insert into words (word, meaning, language_ID) values ("aut�usz", 18, 1); //values ut�ni �rt�kek kicser�l�se v�ltoz�ra
insert into words (word, meaning, language_ID) values ("aut�usz", NULL, 1); //ha nincs m�g p�rja
//az fontos lehet hogy a sz� mindig arra a sz�ra mutasson ami r� mutat (oda-vissza mutassanak)


//szavak t�rl�se
delete from words where (word = "busz"  and Language_ID=1); //m�t� szerint �szes word mez�vel megegyzez� �s nyelv� sz�t and meaning= (select  ID from words where ID=1);
select  ID from words where ID=(select meaning from words where Language_ID= 1);
select * from words where Language_ID=1;

update words SET meaning= NULL where meaning = 9;  // szavak t�rl�sekor a r�mutat� szavak meaningje legyen null  
update words SET meaning= NULL where meaning = (select ID from words where Language_ID=2 and word="bus" ); 

 //szavak m�dos�t�sa
 update words Set word=""  where ID=1;
 update words Set meaning=5 where ID=1;
 update words Set Language_ID=2 where ID=1;
 
 //szavak list�z�sa
 select * from words where  Language_ID = (select ID from  languages where Name = 'N�met' ); //azok a szavak list�z�sa melyek .. nyev�ek

 select * from words where  Language_ID = (select ID from  languages where Name = 'N�met')and word like  '%us%'; //azok a szavak list�z�sa melyek .. nyelv�ek �s .. r�sz van a sz�ban

SELECT * FROM `words` WHERE Meaning = (select ID from words where word="bus" and 
 Language_ID= (select ID from  languages where Name = 'N�met')) and
 Language_ID = (select ID from  languages where Name = 'Magyar'); //a .. sz�nak mely .. nyelv�, a .. nyelv� p�rjai
 
 //nyelvek felvite
 insert into Languages (Name) values ("Arab"); 
 
 //nyelvek t�rl�se
 delete from Languages where Name='Arab';
 
 //nyelvek m�dos�t�sa
 update Languages SET Name="arab" where Name="Arab";
 
 //nyelvek list�z�sa
 select Name from Languages;
 
 
 
 
 
 update words Set word= "buszocska"  where word= "aut�" and nyelv=nyelv and meaning =1;
 update words SET word= "" where meaning = (select ID from words where Language_ID=2 and word="bus" ); 