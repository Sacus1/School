#!/bin/bash

# changement de propriétaire des fichiers sources 
# (un "chown" pourra aussi fonctionner mais vous ne pourrez plus éditer vos fichier)
chmod -R 777 /var/www/html

# lancement du server apache
apache2-foreground
