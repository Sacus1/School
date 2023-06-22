DROP SCHEMA IF EXISTS `course`;
CREATE DATABASE `course`;
USE `course`;

CREATE TABLE `Epreuve` (
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `titre` varchar(120) NOT NULL,
  `ville` varchar(30) NOT NULL,
  `date` date NOT NULL,
  `distance` decimal(5,2) NOT NULL,
  `denivelle` integer NOT NULL,
  `min` integer NOT NULL,
  `max` integer NOT NULL,
  `coord` point NOT NULL
) ENGINE=InnoDB;

CREATE TABLE Categorie (
  `id` varchar(3) PRIMARY KEY,
  `categorie` varchar(30) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE Participant (
  `id` integer PRIMARY KEY,
  `prenom` varchar(30) NOT NULL,
  `nom` varchar(30) NOT NULL,
  `nationalite` varchar(3) NOT NULL,
  `categorie` varchar(3) NOT NULL,
  `naissance` date NOT NULL,
  `temps` TIME NOT NULL,
  CONSTRAINT `Participant_Categorie_fk` FOREIGN KEY (`categorie`) REFERENCES `Categorie` (`id`)
) ENGINE=InnoDB;

CREATE TABLE Points (
  `id` varchar(6) PRIMARY KEY,
  `epreuve` integer NOT NULL,
  `coord` POINT,
  CONSTRAINT `Points_Epreuve_fk` FOREIGN KEY (`epreuve`) REFERENCES `Epreuve` (`id`)
) ENGINE=InnoDB;

CREATE TABLE Controles(
    `id` integer PRIMARY KEY AUTO_INCREMENT,
    `participant` integer NOT NULL,
    `point` varchar(6) NOT NULL,
    `horodatage` DATETIME NOT NULL,
    CONSTRAINT `Controles_Participant_fk` FOREIGN KEY (`participant`) REFERENCES `Participant` (`id`),
    CONSTRAINT `Controles_Points_fk` FOREIGN KEY (`point`) REFERENCES `Points` (`id`)
    ) ENGINE=InnoDB;
