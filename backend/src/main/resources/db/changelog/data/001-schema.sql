create table if not exists form_data
(
    id   bigint auto_increment primary key,
    first_name varchar(255) null,
    last_name varchar(255) null,
    email varchar(255) null,
    phone varchar(255) null
);

