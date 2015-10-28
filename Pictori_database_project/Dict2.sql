insert into nyelvek VALUES(1, 'Magyar');
select * from nyelvek;
insert into szavak values(1,1,'király');
select * from szavak;
insert into szótárak values(1, 1, 2);
select * from szótárak;
insert into nyelvek values(2, 'Angol');
insert into szavak values (2,2, 'king');
insert into szópárok values (1,1,1,2);
select * from szópárok;
select * from szavak;
select * from szavak inner join szópárok on szavak.ID_Szavak = szópárok.Szó_1_ID and ... where "király" =  szavak...;


select Szó_neve from szavak where Nyelv_ID = (select ID_Nyelvek from  Nyelvek where Nyelv_Neve = 'Angol');
