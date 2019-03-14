create table mark (
  mark_id      serial primary key,
  manufacturer text,
  model        text
);

create table body (
  body_id   serial primary key,
  body_type text,
  color     text
);

create table age (
  age_id           serial primary key,
  manufacture_year int,
  newness          text,
  mileage          bigint
);

create table engine (
  engine_id     serial primary key,
  engine_type   text,
  engine_volume int
);

create table chassis (
  chassis_id        serial primary key,
  transmission_type text
);

create table car (
  car_id     serial primary key,
  price      int,
  mark_id    int references mark(mark_id),
  body_id    int references body(body_id),
  age_id     int references age(age_id),
  engine_id  int references engine(engine_id),
  chassis_id int references chassis(chassis_id)
);