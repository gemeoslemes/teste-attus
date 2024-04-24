create table enderecos(
    id bigint auto_increment primary key,
    logradouro varchar(150),
    cep int not null,
    numero int not null,
    cidade varchar(200) not null,
    estado ENUM('AC', 'AL', 'AP', 'AM', 'BA', 'CE', 'DF', 'ES', 'GO', 'MA', 'MT', 'MS', 'MG', 'PA', 'PB', 'PR', 'PE', 'PI', 'RJ', 'RN', 'RS', 'RO', 'RR', 'SC', 'SP', 'SE', 'TO') NOT NULL,
    pessoa_id BIGINT,
    FOREIGN KEY (pessoa_id) REFERENCES pessoas(id)
);
