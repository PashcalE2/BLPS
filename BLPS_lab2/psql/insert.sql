
-- Карточки

insert into BankCard values (default, '1234 1234 1234 1234', '12/1234', '123', 123);
insert into BankCard values (default, '4321 4321 4321 4321', '12/1234', '321', 1234);

-- Клиенты

insert into Client values (default, 'a@a.a', '123', null, null, null);
insert into Client values (default, 'b@b.b', '123', '1234 1234 1234 1234', '12/1234', '123');
insert into Client values (default, 'c@c.c', '123', '4321 4321 4321 4321', '12/1234', '321');

-- Курсы

insert into Course values (default, 'cheap', 99);
insert into Course values (default, 'expensive', 999);

-- Записи на курсы