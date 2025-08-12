CREATE TABLE todo_user (
   id SERIAL PRIMARY KEY,
   name TEXT unique not null,
   login TEXT  not null,
   password TEXT  not null
);
