insert into users(uuid, email, password, is_active) values (gen_random_uuid(), 'a@a.com', '123', true);
insert into users(uuid, email, password, is_active) values (gen_random_uuid(), 'b@a.com', '321', true);
insert into users(uuid, email, password, is_active) values (gen_random_uuid(), 'bad@a.com', '123', false);