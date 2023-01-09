# Prototype Dockerfile to have Memory Alpha as a Docker container.
# Sadly due to XML dumps being quite old, it wouldn't make sense to use this, and it would need some polishing anyway,
# like moving XML import from startup-ma.sh to this file.
FROM mysql:8.0.31-debian

RUN apt update -y
RUN apt upgrade -y

RUN apt install -y wget
RUN wget https://releases.wikimedia.org/mediawiki/1.39/mediawiki-1.39.1.tar.gz
RUN mkdir /var/www/
RUN mkdir /var/www/html/
RUN mkdir /var/www/html/mediawiki/
RUN mkdir /tmp/mediawiki-1.39.1/
RUN tar -xzvf mediawiki-1.39.1.tar.gz -C /tmp/
RUN mv /tmp/mediawiki-1.39.1/* /var/www/html/mediawiki
RUN chown -R $USER:$USER /var/www/html/mediawiki
RUN wget https://s3.amazonaws.com/wikia_xml_dumps/e/en/enmemoryalpha_pages_current.xml.7z
RUN apt install -y p7zip-full
RUN mkdir /tmp/ma
RUN 7z e -o/tmp/ma/ enmemoryalpha_pages_current.xml.7z
RUN ln -s /var/lib/mediawiki /var/www/html/mediawiki

RUN apt install -y software-properties-common ca-certificates lsb-release apt-transport-https
RUN sh -c 'echo "deb https://packages.sury.org/php/ $(lsb_release -sc) main" > /etc/apt/sources.list.d/php.list'
RUN wget -qO - https://packages.sury.org/php/apt.gpg | apt-key add -
RUN apt update
RUN apt install -y apache2
RUN apt install -y php8.1
RUN apt install -y php8.1-mbstring php8.1-xml php8.1-intl php8.1-mysql
RUN a2enmod rewrite
RUN sed -i "s/memory_limit = 128M/memory_limit = 1000M/g" /etc/php/8.1/apache2/php.ini

COPY startup-ma.sh /startup-ma.sh
ENTRYPOINT ["./startup-ma.sh"]

