create type "RoleEnum" as enum (
    'ADMIN',
    'CLIENT'
);

create table if not exists "User"(
    id bigserial primary key,
    email text unique not null check (email ~ '\w+@\w+\.\w+'),
    password text not null,
    role "RoleEnum" not null,
    banned bool not null
);

create table if not exists "RefreshToken"(
    id bigserial primary key,
    user_id bigint references "User"(id) not null,
    token text unique not null,
    expires_at timestamp not null
);

create table if not exists "BankCard"(
    id bigserial primary key,
    user_id bigint references "User"(id),
    serial_number text unique not null check (serial_number ~ '\d\d\d\d \d\d\d\d \d\d\d\d \d\d\d\d'),
    validity_date text not null check (validity_date ~ '\d\d/\d\d\d\d'),
    cvv text not null check (cvv ~ '\d\d\d'),
    money int not null check (money >= 0)
);

create table if not exists "Course"(
    id bigserial primary key,
    name text unique not null,
    price int not null check (price > 0)
);

create table if not exists "UsersCourses"(
    user_id bigint references "User"(id),
    course_id bigint references "Course"(id),
    primary key (user_id, course_id)
);
