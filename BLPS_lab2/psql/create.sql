create table if not exists Client(
    id bigserial primary key,
    email text unique not null check (email ~ '\w+@\w+\.\w+'),
    password text not null,
    card_serial text,
    card_validity text,
    card_cvv text
);

create table if not exists ClientBan(
    id bigserial primary key,
    client_id bigint references Client(id),
    banned_on timestamp not null,
    unban_on timestamp,
    comment text
);

create table if not exists BankCard(
    id bigserial primary key,
    serial_number text unique not null check (serial_number ~ '\d\d\d\d \d\d\d\d \d\d\d\d \d\d\d\d'),
    validity_date text not null check (validity_date ~ '\d\d/\d\d\d\d'),
    cvv text not null check (cvv ~ '\d\d\d'),
    money int not null check (money >= 0)
);

create table if not exists Course(
    id bigserial primary key,
    name text unique not null,
    price int not null check (price > 0)
);

create table if not exists ClientsCourses(
    client_id bigint references Client(id),
    course_id bigint references Course(id),
    primary key (client_id, course_id)
);
