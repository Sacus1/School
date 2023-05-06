LOAD DATA INFILE '/docker-entrypoint-initdb.d/cinema-personne.csv'
REPLACE INTO TABLE Personne
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\r\n'
IGNORE 1 LINES
(id,nom,prenom,naissance,@deces,nationalite,@artiste)
SET
deces = NULLIF(@deces,''), artiste = NULLIF(@artiste,'');

LOAD DATA INFILE '/docker-entrypoint-initdb.d/cinema-film.csv'
REPLACE INTO TABLE Film
FIELDS TERMINATED BY ',' ENCLOSED BY '\"'
LINES TERMINATED BY '\r\n'
IGNORE 1 LINES
(id,titre,titre_original,annee,sortie,duree);

LOAD DATA INFILE '/docker-entrypoint-initdb.d/cinema-equipe.csv'
REPLACE INTO TABLE Equipe
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\r\n'
IGNORE 1 LINES
(id, film, personne, role, alias);

LOAD DATA INFILE '/docker-entrypoint-initdb.d/cinema-etablissement.csv'
REPLACE INTO TABLE Cinema
FIELDS TERMINATED BY ',' ENCLOSED BY '\"'
LINES TERMINATED BY '\r\n'
IGNORE 1 LINES
(id,nom,voie,ville,@latitude,@longitude)
SET
coordonnees = POINT(@latitude, @longitude);

LOAD DATA INFILE '/docker-entrypoint-initdb.d/cinema-genre.csv'
REPLACE INTO TABLE Genre
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\r\n'
IGNORE 1 LINES
(id,genre);

LOAD DATA INFILE '/docker-entrypoint-initdb.d/cinema-salle.csv'
REPLACE INTO TABLE Salle
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\r\n'
IGNORE 1 LINES;

LOAD DATA INFILE '/docker-entrypoint-initdb.d/cinema-seance.csv'
REPLACE INTO TABLE Seance
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\r\n'
IGNORE 1 LINES;
