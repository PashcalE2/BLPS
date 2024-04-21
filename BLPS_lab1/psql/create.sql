create table Client(
    id bigserial primary key,
    email text unique not null,
    password text not null,
    card_serial text,
    card_validity text,
    card_cvv text
);

create table BankCard(
    id bigserial primary key,
    serial_number text unique not null,
    validity_date text,
    cvv text
);

create table Course(
    id bigserial primary key,
    name text unique not null,
    price int
);

create table ClientsCourses(
    client_id bigint references Client(id),
    course_id bigint references Course(id),
    primary key (client_id, course_id)
);
