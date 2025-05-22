create table if not exists results
(
    id   bigint auto_increment primary key,
    category varchar(255) null,
    question varchar(255) null,
    correct_answer varchar(255) null,
    answered_correctly tinyint(1) null,
    form_data_id bigint not null,

        constraint fk_form_data
        foreign key (form_data_id)
        references form_data(id)
        on delete restrict
        on update cascade
);

create table if not exists jackpot_history (
    id bigint auto_increment primary key,
    form_data_id bigint not null,
    result_id bigint null,
    jackpot_id bigint null,

    constraint fk_form_data_jackpot_history
        foreign key (form_data_id)
        references form_data(id)
        on delete restrict
        on update cascade,

    constraint fk_result
        foreign key (result_id)
        references results(id)
        on delete set null
        on update cascade,

    constraint fk_jackpot
        foreign key (jackpot_id)
        references jackpots(id)
        on delete set null
        on update cascade
);
