CREATE TABLE professores (
    id BIGINT NOT NULL
    GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    nome VARCHAR(255) NOT NULL,
    matricula INT NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO professores (nome, matricula) VALUES ('Bob', 1234);
INSERT INTO professores (nome, matricula) VALUES ('Ana', 4321);
INSERT INTO professores (nome, matricula) VALUES ('Charles', 5555);
