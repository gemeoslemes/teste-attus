create table pessoas(
    id bigint auto_increment primary key,
    nome varchar(200) not null,
    data_nascimento date not null
);