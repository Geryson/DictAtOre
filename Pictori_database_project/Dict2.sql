insert into nyelvek VALUES(1, 'Magyar');
select * from nyelvek;
insert into szavak values(1,1,'kir�ly');
select * from szavak;
insert into sz�t�rak values(1, 1, 2);
select * from sz�t�rak;
insert into nyelvek values(2, 'Angol');
insert into szavak values (2,2, 'king');
insert into sz�p�rok values (1,1,1,2);
select * from sz�p�rok;
select * from szavak;
select * from szavak inner join sz�p�rok on szavak.ID_Szavak = sz�p�rok.Sz�_1_ID and ... where "kir�ly" =  szavak...;


select Sz�_neve from szavak where Nyelv_ID = (select ID_Nyelvek from  Nyelvek where Nyelv_Neve = 'Angol');
