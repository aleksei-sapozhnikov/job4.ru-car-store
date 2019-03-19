create table mark (
  mark_id      bigserial primary key,
  manufacturer text,
  model        text
);

create table body (
  body_id   bigserial primary key,
  body_type text,
  color     text
);

create table age (
  age_id           bigserial primary key,
  manufacture_year int,
  newness          text,
  mileage          bigint
);

create table engine (
  engine_id     bigserial primary key,
  engine_type   text,
  engine_volume int
);

create table chassis (
  chassis_id        bigserial primary key,
  transmission_type text
);

create table image (
  image_id   bigserial primary key,
  image_data oid
);

create table car (
  car_id     bigserial primary key,
  price      int,
  mark_id    int references mark(mark_id),
  body_id    int references body(body_id),
  age_id     int references age(age_id),
  engine_id  int references engine(engine_id),
  chassis_id int references chassis(chassis_id)
);

create table car_image (
  car_id   bigint,
  image_id bigint
);

create table users (
  user_id  bigserial primary key,
  login    text unique,
  password text,
  phone    text,
  address  text
);

create table user_car (
  user_id bigint,
  car_id  bigint
);
