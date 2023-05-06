USE cinema

CREATE TABLE Etablissement (
    id integer PRIMARY KEY NOT NULL,
    nom varchar(100) NOT NULL,
    voie varchar(100) NOT NULL,
    ville varchar(100) NOT NULL,
    coordonnees POINT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOAD DATA INFILE "/docker-entrypoint-initdb.d/etablissement.csv" REPLACE INTO TABLE Etablissement
FIELDS TERMINATED BY ',' ENCLOSED BY '\"'
LINES TERMINATED BY '\r\n'
IGNORE 1 ROWS
(id,nom, voie, ville, @x, @y)
SET coordonnees = POINT(@x, @y);
