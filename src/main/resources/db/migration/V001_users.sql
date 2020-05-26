create table users
(
	id varchar not null,
	username varchar,
	password varchar,
	pic varchar,
	date created_at default now()

);
