# Image de base sur DockerHub
FROM php:8.0-apache

# Ajout du module de rewriting d'Apache
RUN a2enmod rewrite

# Mise à jour du gestionnaire de paquets apt-get et installation de zip / unzip
RUN apt-get update && apt-get install -y unzip zip git

# installation de node pour npm (partie Frontend + plus tard)
RUN apt-get update -y && apt-get upgrade -y && apt-get install -y && \
    curl -sL https://deb.nodesource.com/setup_16.x | bash - && apt-get install -y nodejs

# Changement du workdir principal pour qu'il pointe vers /var/www/html
# (dossier d'entrée du serveur d'application ou se trouvent les sources)
WORKDIR /var/www/html

# Installation de quelques extensions php indispensables pour notre projet
COPY --from=mlocati/php-extension-installer /usr/bin/install-php-extensions /usr/local/bin/
RUN install-php-extensions gd pdo_mysql bcmath zip intl opcache

# Installation de composer en utilisant le "build multistage" permettant d’exécuter des sous
# parties du Dockerfile d'une autre image (plus concis qu'une install 'classique' avec apt-get)
COPY --from=composer:2.0 /usr/bin/composer /usr/local/bin/composer

# Copie du fichier entrypoint dans l'image Docker ==> dans le dossier "/opt" (cmd ADD)
ADD entrypoint.sh /opt

# Ajout des droits d'exécution chmod 744 sur ce dernier fichier
RUN chmod 744 /opt/entrypoint.sh
# Execution de l'entry point "/opt/entrypoint.sh" lors du démarrage du conteneur
ENTRYPOINT [ "/opt/entrypoint.sh" ]
