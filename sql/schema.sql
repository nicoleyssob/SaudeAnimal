CREATE DATABASE IF NOT EXISTS saudeanimal CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE saudeanimal;

CREATE TABLE IF NOT EXISTS Animal (
    IdAnimal INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    Nome VARCHAR(50) NOT NULL,
    Raca VARCHAR(50),
    Genero VARCHAR(20),
    Nascimento DATE,
    PesoKg DECIMAL(7,2),
    AlturaCm DECIMAL(6,2)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS HistoricoSaude (
    IdHistorico INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    IdAnimal INT NOT NULL,
    DataConsulta DATE NOT NULL,
    Diagnostico VARCHAR(300),
    Tratamento VARCHAR(300),
    CONSTRAINT FK_HistoricoSaude_Animal FOREIGN KEY (IdAnimal)
        REFERENCES Animal (IdAnimal) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS Vacinas (
    IdVacinas INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    IdAnimal INT NOT NULL,
    NomeVacina VARCHAR(50) NOT NULL,
    DataAplicacao DATE NOT NULL,
    CONSTRAINT FK_Vacinas_Animal FOREIGN KEY (IdAnimal)
        REFERENCES Animal (IdAnimal) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS Genetica (
    IdGenetica INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    IdAnimal INT NOT NULL,
    DoencasGeneticas VARCHAR(50),
    InformacoesGeneticas VARCHAR(500),
    CONSTRAINT FK_Genetica_Animal FOREIGN KEY (IdAnimal)
        REFERENCES Animal (IdAnimal) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS SurtosDoencas (
    IdSurto INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    NomeSurto VARCHAR(100) NOT NULL,
    DataInicio DATE NOT NULL,
    DataFim DATE
) ENGINE=InnoDB;
