create table if not exists user
(
    id   bigint auto_increment primary key,
    username varchar(255) null,
    password_hash varchar(128) null,
    form_data_id bigint null unique,
    foreign key (form_data_id) references form_data(id)
);
