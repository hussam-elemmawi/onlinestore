create schema public
;

alter schema public owner to root777
;

create table if not exists product
(
	product_id bigserial not null
		constraint product_pkey
			primary key,
	code varchar(50) default 'no code'::character varying not null,
	price double precision default 0.0 not null,
	offer_price double precision default 0.0 not null,
	quantity integer default 0 not null,
	product_name varchar(1000) not null,
	product_description varchar(99999) not null,
	coming_date timestamp default now() not null,
	updated_at timestamp default now() not null,
	created_at timestamp default now() not null
)
;

alter table product owner to root777
;

create unique index if not exists product_product_id_uindex
	on product (product_id)
;

create table if not exists customer
(
	customer_id bigserial not null
		constraint customer_pkey
			primary key,
	ssn varchar(14),
	customer_name varchar(100)
)
;

alter table customer owner to root777
;

create unique index if not exists customer_customer_id_uindex
	on customer (customer_id)
;

create unique index if not exists customer_ssn_uindex
	on customer (ssn)
;

create unique index if not exists customer_customer_name_uindex
	on customer (customer_name)
;

create table if not exists promotion
(
	promotion_id bigserial not null
		constraint promotion_pkey
			primary key,
	promotion_description varchar(9999) not null,
	high_price double precision default 0.0 not null,
	low_price double precision default 0.0 not null,
	high_percent double precision default 0.0 not null,
	low_precent double precision default 0.0 not null,
	free_ship boolean default false not null
)
;

alter table promotion owner to root777
;

create unique index if not exists promotion_promotion_id_uindex
	on promotion (promotion_id)
;

create table if not exists tag
(
	tag_id bigserial not null
		constraint tag_pkey
			primary key,
	tag_name varchar(100),
	value varchar(100)
)
;

alter table tag owner to root777
;

create unique index if not exists tag_tag_id_uindex
	on tag (tag_id)
;

create unique index if not exists tag_tag_name_value_tag_id_uindex
	on tag (tag_name, value, tag_id)
;

create table if not exists order_status
(
	order_status_id bigserial not null
		constraint order_status_pkey
			primary key,
	status_name varchar(100)
)
;

alter table order_status owner to root777
;

create unique index if not exists order_status_order_status_id_uindex
	on order_status (order_status_id)
;

create unique index if not exists order_status_order_status_id_status_name_uindex
	on order_status (order_status_id, status_name)
;

create table if not exists address
(
	customer_id bigint not null
		constraint address_customer__fk
			references customer
				on update cascade on delete cascade,
	address varchar(150) not null,
	constraint address_pk
		primary key (customer_id, address)
)
;

alter table address owner to root777
;

create table if not exists phone
(
	customer_id bigint not null
		constraint phone_customer__fk
			references customer
				on update cascade on delete cascade,
	phone varchar(11) not null,
	constraint phone_pk
		primary key (customer_id, phone)
)
;

alter table phone owner to root777
;

create table if not exists "order"
(
	order_id bigserial not null
		constraint order_pkey
			primary key,
	customer_id bigint not null
		constraint order_customer__fk
			references customer,
	date timestamp default now() not null,
	order_status_id bigint not null
		constraint order_order_status__fk
			references order_status,
	total_price double precision default 0.0 not null,
	promotion_id bigint not null
		constraint order_promotion__fk
			references promotion,
	offer_price double precision default 0.0 not null,
	ship_address varchar(150) not null
)
;

alter table order_info owner to root777
;

create unique index if not exists order_order_id_uindex
	on "order" (order_id)
;

create index if not exists order_customer_id_index
	on "order" (customer_id)
;

create index if not exists order_order_status_id_index
	on "order" (order_status_id)
;

create index if not exists order_promotion_id_index
	on "order" (promotion_id)
;

create table if not exists product_tag_relation
(
	product_id bigint not null
		constraint product_tag_relation_product__fk
			references product
				on update cascade on delete cascade,
	tag_id bigint not null
		constraint product_tag_relation_tag__fk
			references tag
				on update cascade on delete cascade,
	constraint product_tag_relation_pk
		primary key (product_id, tag_id)
)
;

alter table product_tag_relation owner to root777
;

create unique index if not exists product_tag_relation_product_id_tag_id_uindex
	on product_tag_relation (product_id, tag_id)
;

create table if not exists order_product_relation
(
	order_id bigint not null
		constraint order_product_relation_order__fk
			references "order"
				on update cascade on delete cascade,
	product_id bigint not null
		constraint order_product_relation_product__fk
			references product
				on update cascade,
	order_quantity integer default 0 not null,
	product_total_price double precision default 0.0 not null,
	constraint order_product_relation_pk
		primary key (order_id, product_id)
)
;

alter table order_product_relation owner to root777
;

