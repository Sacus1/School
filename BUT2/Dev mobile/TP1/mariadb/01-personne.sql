USE cinema

CREATE TABLE Personne (
    id uuid PRIMARY KEY DEFAULT UUID() NOT NULL,
    nom varchar(100) NOT NULL,
    prenom varchar(100) NOT NULL,
    naissance date NOT NULL,
    deces date,
    nationalite varchar(100) NOT NULL,
    artiste varchar(100)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOAD DATA INFILE "/docker-entrypoint-initdb.d/personnes.csv" REPLACE INTO TABLE Personne
FIELDS TERMINATED BY ','
ENCLOSED BY '"' LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(id, nom, prenom, naissance, deces, nationalite, artiste);