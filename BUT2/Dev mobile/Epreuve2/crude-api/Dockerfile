FROM php:7.1-cli-alpine
WORKDIR /crude-api
RUN docker-php-ext-install pdo pdo_mysql
ADD https://github.com/mevdschee/php-crud-api/releases/download/v2.14.19/api.php index.php
EXPOSE 80
CMD [ "php","-S","0.0.0.0:80" ]
