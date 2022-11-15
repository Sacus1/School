# SAE2-Sujet-11-JeuCombat-tp-3-Poirot-Marchal-Marchal-Lecointre-Paquin



## Fonctionnalitées
- Animations
- Système de vie
- Gestion des entrée clavier
- Gestion d'attaques et de dégats via collision
- Gestion d'une Victoire
- Possibilitée de rejouer après une partie

#### Ce qui n'a pas été fait
- Un menu fonctionnel.

Après pas mal de galères et d'echec, l'idée d'un menu fontionnel a été abandonner, faut de réussite.

- La gestion des bordures

Il est malheureusement possible de tomber de la carte si un joueur s'aventure trop loin sur les bords, hors de l'écran.


## Description
Il s'agit d'un jeu de combat, de deux joueurs qui doivent partager le même clavier afin d'éliminer l'adversaire.

## Utilisation
Chaque joueurs controle un des héros.
> **Le premier joueur devra utiliser**

Q -> Aller a gauche

D -> Aller a droite

Z -> Sauter

A -> Attaque légère

E -> Attaque lourde

> **Le second joueur devra utiliser**
K -> Aller a gauche

M -> Aller a droite

O -> Sauter

P -> Attaque légère

I -> Attaque lourde
![Control](/doc/Control.png)
## Règle du jeu
Le jeu se présente sous la forme d'un jeu de combat ente deux joueurs, chacun des joueurs possède une barre de vie indiquée a haut de l'écran. Le but est simplement d'utiliser les deux types d'attaques disponible dans le jeu afin d'attaquer son adversaire et lui infliger des dégâts. Le premier qui arriver a faire tomber les points de vie de son adversaire a 0 a gagner.


# Règles du jeu

- Partie en 1 manche
- Le premier a 0 vie pert la partie

# Joueurs

- PV -> 100
- 2 attaques disponibles
- Déplacement en haut, a droite et a gauche

# Attaques

*1 dmg infliger = 1 pv perdu*

> Coup legère
- 4 dmg
- Durée en Frames -> 40

> Coup lourde
- 6 dmg
- Durée en Frames -> 50
