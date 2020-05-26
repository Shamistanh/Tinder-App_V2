create table reactions
(
	id varchar not null,
	who varchar,
	reaction varchar default 0,
	date date default now()
);
