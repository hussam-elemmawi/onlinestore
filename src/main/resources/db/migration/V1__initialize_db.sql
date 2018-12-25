create table product
(
  id           serial                       not null
    constraint product_pkey
    primary key,
  product_code varchar(43)                  not null,
  name         varchar(255)                 not null,
  description  text                         not null,
  quantity     integer default 0            not null,
  price        double precision default 0.0 not null,
  created_at   timestamp default now()      not null,
  updated_at   timestamp default now()      not null
);

alter table product
  owner to nothing;

create unique index product_id_uindex
  on product (id);

create unique index product_productcode_uindex
  on product (product_code);

create table tag
(
  id        serial                  not null
    constraint tag_pkey
    primary key,
  name      varchar(100)            not null,
  value     varchar(100)            not null,
  createdat timestamp default now() not null
);

alter table tag
  owner to nothing;

create unique index tag_id_uindex
  on tag (id);

create unique index tag_name_uindex
  on tag (name);

create table product_tag_relation
(
  id         serial  not null
    constraint product_tag_relation_pkey
    primary key,
  product_id integer not null
    constraint product_tag_relation_product_id_fk
    references product,
  tag_id     integer not null
    constraint product_tag_relation_tag_id_fk
    references tag
);

alter table product_tag_relation
  owner to nothing;

create unique index product_tag_relation_id_uindex
  on product_tag_relation (id);

create unique index product_tag_relation_product_id_tag_id_uindex
  on product_tag_relation (product_id, tag_id);

