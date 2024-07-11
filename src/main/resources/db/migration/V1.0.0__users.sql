create table users
(
    uuid      uuid                  not null
        constraint users_pk
            primary key,
    email     varchar               not null
        constraint users_email_uk
            unique,
    password  varchar,
    is_active boolean default false not null
);