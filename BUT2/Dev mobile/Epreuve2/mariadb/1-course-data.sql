LOAD DATA INFILE '/docker-entrypoint-initdb.d/categorie.csv'
REPLACE INTO TABLE Categorie
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\r\n'
IGNORE 1 LINES
(id,categorie);

LOAD DATA INFILE '/docker-entrypoint-initdb.d/epreuve.csv'
REPLACE INTO TABLE Epreuve
FIELDS TERMINATED BY ',' ENCLOSED BY '\"'
LINES TERMINATED BY '\r\n'
IGNORE 1 LINES
(id,titre,ville,date,distance,denivelle,min,max,@point)
SET
coord = ST_PointFromText(@point);
LOAD DATA INFILE '/docker-entrypoint-initdb.d/points.csv'
REPLACE INTO TABLE Points
FIELDS TERMINATED BY ',' ENCLOSED BY '\"'
LINES TERMINATED BY '\r\n'
IGNORE 1 LINES
(id,epreuve,@point)
SET
    coord = ST_PointFromText(@point);
LOAD DATA INFILE '/docker-entrypoint-initdb.d/participant.csv'
REPLACE INTO TABLE Participant
FIELDS TERMINATED BY ',' ENCLOSED BY '\"'
LINES TERMINATED BY '\r\n'
IGNORE 1 LINES
(id,prenom,nom,nationalite,categorie,naissance,temps);
