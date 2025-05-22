create table if not exists jackpots
(
    id   bigint auto_increment primary key,
    name varchar(255) null,
    description varchar(255) null
);
insert into jackpots (name, description)
values
("Losing Ticket", "Ooops, that's a blank. Good luck next time!"),
("Software Developer Job at Key-Work", "Congratulations! You won a job."),
("Free Hug", "You only need to find a person to receive it from :)"),
("One More Try", "Congrats! You're eligible to play the game once more.");
