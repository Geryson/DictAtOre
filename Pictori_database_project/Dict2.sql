insert into words values (18,"bus",   19,  2);
insert into words values (23,"busz",   20,  1);
insert into languages values(1, "Magyar");
insert into languages values(2, "Angol");
insert into languages values(3, "N�met");
insert into words values (25,"aut�busz", 18, 1);
 
select Name from Languages; //nyelvek list�z�sa

select * from words where  nyelv = (select ID from  languages where Name = 'N�met' ); //azok a szavak list�z�sa melyek .. nyev�ek

select * from words where  nyelv = (select ID from  languages where Name = 'N�met')and word like  '%us%'; //azok a szavak list�z�sa melyek .. nyelv�ek �s .. r�sz van a sz�ban

SELECT * FROM `words` WHERE Meaning = (select ID from words where word="bus" and 
nyelv = (select ID from  languages where Name = 'N�met')) and
nyelv = (select ID from  languages where Name = 'Magyar'); //a .. sz�nak mely .. nyelv�, a .. nyelv� p�rjai

select * from languages;

select * from words;