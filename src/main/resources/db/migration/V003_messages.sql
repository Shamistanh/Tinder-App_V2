create table messages
(
	id varchar not null,
	who varchar,
	whom varchar,
	message varchar,
	date date default now()
);