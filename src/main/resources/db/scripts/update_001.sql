create table users (
  user_id  bigserial primary key,
  login    varchar(255) unique,
  password varchar(255),
  phone    varchar(255)
);

create table if not exists car (
  car_id                    bigserial not null primary key,
  age_manufacture_year      integer,
  age_mileage               bigint,
  age_newness               varchar(255),
  body_color                varchar(255),
  body_type                 varchar(255),
  chassis_transmission_type varchar(255),
  engine_type               varchar(255),
  engine_volume             integer,
  mark_manufacturer         varchar(255),
  mark_model                varchar(255),
  car_price                 integer,
  user_seller               bigint references users(user_id)
);

create table if not exists image (
  image_id   bigserial primary key,
  image_data oid,
  car_id     bigint references car(car_id)
);