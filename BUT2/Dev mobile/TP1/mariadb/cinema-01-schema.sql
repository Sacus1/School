DROP SCHEMA IF EXISTS `cinema`;
CREATE DATABASE `cinema`;
USE `cinema`;

CREATE TABLE `Personne` (
  `id` UUID NOT NULL DEFAULT UUID(),
  `nom` varchar(30) NOT NULL,
  `prenom` varchar(30) NOT NULL,
  `naissance` date,
  `deces` date,
  `artiste` varchar(30),
  `nationalite` char(2),
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NULL ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE Film (
  `id` uuid NOT NULL DEFAULT UUID(),
  `titre` varchar(80) NOT NULL,
  `titre_original` varchar(80),
  `annee` integer,
  `sortie` date,
  `duree` integer,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NULL ON UPDATE current_timestamp(),
  `note` decimal(4,3),
  `n_notes` integer,
  PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE Cinema (
  `id` integer PRIMARY KEY,
  `nom` character varying(60),
  `voie` character varying(60),
	`ville` character varying(40),
	`coordonnees` geometry,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NULL ON UPDATE current_timestamp()
) ENGINE=InnoDB;

CREATE TABLE Equipe (
  `id` integer NOT NULL AUTO_INCREMENT,
  `film` uuid NOT NULL,
  `personne` uuid NOT NULL,
  `role` varchar(25),
  `alias` varchar(40),
  PRIMARY KEY (`id`),
  KEY `film` (`film`),
  CONSTRAINT `Equipe_Film_fk` FOREIGN KEY (`film`) REFERENCES `Film` (`id`),
  KEY `personne` (`personne`),
  CONSTRAINT `Equipe_Personne_fk` FOREIGN KEY (`personne`) REFERENCES `Personne` (`id`)
) ENGINE=InnoDB;

CREATE TABLE `Societe` (
  `id` uuid PRIMARY KEY,
  `societe` varchar(30) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE `Genre` (
  `id` integer PRIMARY KEY,
  `genre` varchar(30) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE `Salle` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `cinema` int(11) NOT NULL,
  `salle` varchar(20) NOT NULL,
  `sieges` int(11) NOT NULL,
  CONSTRAINT `Salle_ibfk_1` FOREIGN KEY (`cinema`) REFERENCES `Cinema` (`id`)
) ENGINE=InnoDB;


CREATE TABLE `Seance` (
  `id` int(11) PRiMARY KEY,
  `film` uuid NOT NULL,
  `salle` int(11) NOT NULL,
  `seance` datetime NOT NULL,
  CONSTRAINT `Seance_ibfk_1` FOREIGN KEY (`film`) REFERENCES `Film` (`id`),
  CONSTRAINT `Seance_ibfk_2` FOREIGN KEY (`salle`) REFERENCES `Salle` (`id`)
) ENGINE=InnoDB;
