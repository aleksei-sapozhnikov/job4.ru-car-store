create table users (
  id       bigserial    not null primary key,
  login    varchar(255) not null,
  password varchar(255) not null,
  phone    varchar(255) not null
);

create table cars (
  id                bigserial    not null primary key,
  body_type         varchar(255) not null,
  color             varchar(255) not null,
  engine_fuel       varchar(255) not null,
  engine_volume     integer      not null,
  manufacturer      varchar(255) not null,
  mileage           integer      not null,
  model             varchar(255) not null,
  newness           varchar(255) not null,
  price             integer      not null,
  transmission_type varchar(255) not null,
  year_manufactured integer      not null,
  owner             bigint       not null references users(id),
  available         boolean      not null
);

create table images (
  id     bigserial not null primary key,
  data   oid       not null,
  car_id bigint    not null references cars(id)
);