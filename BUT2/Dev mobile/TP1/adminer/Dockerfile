FROM php:cli-alpine

WORKDIR /adminer
ADD https://github.com/vrana/adminer/releases/download/v4.8.1/adminer-4.8.1-mysql-en.php index.php
CMD [ "php","-S","0.0.0.0:8080" ]
EXPOSE 8080
