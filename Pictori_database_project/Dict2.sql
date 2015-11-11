insert into words values (18,"bus",   19,  2);
insert into words values (23,"busz",   20,  1);
insert into languages values(1, "Magyar");
insert into languages values(2, "Angol");
insert into languages values(3, "Német");
insert into words values (25,"autóbusz", 18, 1);
 
select Name from Languages; //nyelvek listázása

select * from words where  nyelv = (select ID from  languages where Name = 'Német' ); //azok a szavak listázása melyek .. nyevûek

select * from words where  nyelv = (select ID from  languages where Name = 'Német')and word like  '%us%'; //azok a szavak listázása melyek .. nyelvûek és .. rész van a szóban

SELECT * FROM `words` WHERE Meaning = (select ID from words where word="bus" and 
nyelv = (select ID from  languages where Name = 'Német')) and
nyelv = (select ID from  languages where Name = 'Magyar'); //a .. szónak mely .. nyelvû, a .. nyelvû párjai

select * from languages;

select * from words;