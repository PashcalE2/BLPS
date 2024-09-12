-- Курсы

insert into "Course" values (default, 'cheap', 99, true);
insert into "Course" values (default, 'expensive', 999, false);


-- Клиенты

insert into "User" values (1, 'a@a.a', '$2a$10$k8pdc.vCNhjR5HivVT6OnO/7ZbpHDUfy9f.UZ4m9gqlfn6V3SAjXa', 'CLIENT', false);
insert into "User" values (2, 'b@b.b', '$2a$10$k8pdc.vCNhjR5HivVT6OnO/7ZbpHDUfy9f.UZ4m9gqlfn6V3SAjXa', 'CLIENT', false);
insert into "User" values (3, 'c@c.c', '$2a$10$k8pdc.vCNhjR5HivVT6OnO/7ZbpHDUfy9f.UZ4m9gqlfn6V3SAjXa', 'CLIENT', true);
insert into "User" values (4, 'admin@a.a', '$2a$10$k8pdc.vCNhjR5HivVT6OnO/7ZbpHDUfy9f.UZ4m9gqlfn6V3SAjXa', 'ADMIN', false);


-- Карточки

insert into "BankCard" values (default, 1, '1234 1234 1234 1234', '12/1234', '123', 1234);
insert into "BankCard" values (default, 2, '4321 4321 4321 4321', '12/1234', '321', 1234);
insert into "BankCard" values (default, 3, '4321 1234 4321 1234', '12/1234', '111', 1234);

