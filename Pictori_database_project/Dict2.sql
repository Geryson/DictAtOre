select word from words where  (select meaning from words where word="busz") =  (select ID from words where nyelv = "2");
select * from words;
insert into words values (18,"bus",   19,  2);
insert into words values (23,"busz",   20,  1);

select * from languages;
insert into languages values(1, "Magyar");
insert into languages values(2, "Angol");
insert into languages values(3, "Német");
select word from words where (nyelv = "Angol") and   ID = (select meaning from words where word="busz");

select * from words where  nyelv = (select ID from  languages where Name = 'Német')  and   ID = 
 (select meaning from words where  (word="busz" and  nyelv= (select ID from  languages where Name = 'Magyar')));
 
 
select * from words where  nyelv = (select ID from  languages where Name = 'Német')and word like  '%us%';
select ID from  languages where Name = 'Angol'; 

select * from words where ( nyelv = (select ID from  languages where Name = 'Német')) ;

select meaning from words where word="busz" and  nyelv = (select ID from  languages where Name = 'Magyar');

select * from words where  nyelv = (select ID from  languages where Name = 'Német' ) and ( ID = "20");

SELECT * FROM `words` WHERE Meaning = 1;

SELECT * FROM `words` WHERE Meaning = (select ID from words where word="bus" and 
nyelv = (select ID from  languages where Name = 'Német')) and
nyelv = (select ID from  languages where Name = 'Magyar');


select nyelv from words where word="bus";
select ID from languages where Name="Angol";

select * from words;

select word from words where ( nyelv = (select ID from  languages where Name = 'Német')) and  
 ID = (select meaning from words where word="busz");
 
 insert into words values (25,"autóbusz", 18, 1);