create table order_book(
	obid INT primary key,
	order_book_name CHAR(50) not null,
	is_open BOOLEAN default 't'
);


create table financial_instrument(

	fid INT primary key,
	fin_inst_name CHAR(50) not null
);

create type order_type as enum('buy', 'sell');

create type execution_type as enum('offer', 'ask');

create table order_record(
	oid VARCHAR(50) primary key,
	obid INT,
	quantity INT not null,
	entry_date DATE NOT NULL DEFAULT CURRENT_DATE,
	otype VARCHAR(10),
	price real,
	fid INT,
	is_complete BOOLEAN default 'f',
	constraint fk_order_record_order_book foreign key(obid) references order_book(obid) on delete cascade,
	constraint fk_order_record_financial_instrument foreign key(fid) references financial_instrument(fid) on delete cascade
);

create table execution(
	eid VARCHAR(50) primary key,
	fid INT,
	quantity INT,
	etype  VARCHAR(10),
	price real,
	is_executed BOOLEAN default 'f',
	constraint fk_execution_financial_instrument foreign key(fid) references financial_instrument(fid) on delete cascade
);

insert into order_book (obid, order_book_name, is_open) values(1, 'my order book', true);

insert into financial_instrument (fid, fin_inst_name) values(1, 'Equity');
insert into financial_instrument (fid, fin_inst_name) values(2, 'Debt');

insert into execution (eid, fid, quantity, etype, price, is_executed) values (1, 1, 20, 'offer', 50, false);
insert into execution (eid, fid, quantity, etype, price, is_executed) values (2, 1, 10, 'ask', 50, false);

commit;