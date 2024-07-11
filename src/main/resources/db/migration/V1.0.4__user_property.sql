create table user_property
(
    property   varchar,
    user_email varchar,
    id         uuid not null
        constraint user_property_pk
            primary key
);