drop table if exists player CASCADE;
drop table if exists videogames CASCADE;

create table player (
    playerId SERIAL primary key,
    username varchar(255) unique,
    password varchar(255)
);

create table videogames (
    gameId SERIAL primary key,
    gameName varchar(255),
    price decimal(10, 2),
    owner int,
    foreign key (owner) references player(playerId)
);