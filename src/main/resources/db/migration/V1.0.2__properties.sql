create table properties
(
    id      varchar not null
        constraint properties_pk
            primary key,
    name    varchar,
    details varchar,
    price   numeric,
    image   varchar
);