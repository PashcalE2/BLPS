-- Курсы

insert into "Course" values (default, 'cheap', 99);
insert into "Course" values (default, 'expensive', 999);


-- Клиенты

insert into "User" values (1, 'a@a.a', '123', 'CLIENT', false);
insert into "User" values (2, 'b@b.b', '123', 'CLIENT', false);
insert into "User" values (3, 'c@c.c', '123', 'CLIENT', true);
insert into "User" values (4, 'admin@a.a', '123', 'ADMIN', false);


-- Карточки

insert into "BankCard" values (default, 1, '1234 1234 1234 1234', '12/1234', '123', 1234);
insert into "BankCard" values (default, 2, '4321 4321 4321 4321', '12/1234', '321', 1234);
insert into "BankCard" values (default, 3, '4321 1234 4321 1234', '12/1234', '111', 1234);


-- Записи на курсы