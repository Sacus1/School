FROM php:cli-alpine
RUN docker-php-ext-install mysqli
WORKDIR /adminer
ADD https://github.com/vrana/adminer/releases/download/v4.8.1/adminer-4.8.1-mysql-en.php index.php
CMD [ "php","-S","0.0.0.0:80" ]
EXPOSE 80
