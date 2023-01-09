#!/bin/bash
export MYSQL_PASSWORD=mysql
export MYSQL_ROOT_PASSWORD=mysql
export MYSQL_USER=mysql
export MYSQL_DATABASE=mysql
touch /tmp/mysql.log
/usr/local/bin/docker-entrypoint.sh mysqld > /tmp/mysql.log &
while true ; do
  result=$(grep -nE 'Ready for start up' /tmp/mysql.log)
  result2=$(grep -nE 'Temporary server stopped' /tmp/mysql.log)
  echo "Waiting for DB to start..."
  if [[ "$result" == *"Ready"* && "$result2" == *"stopped"* ]]; then
    echo "DB started."
    break
  fi
  sleep 3
done
/etc/init.d/apache2 start
php /var/www/html/mediawiki/maintenance/install.php --dbname=mysql --dbserver="127.0.0.1" --installdbuser=mysql --installdbpass=mysql --dbuser=mysql --dbpass=mysql --dbprefix=ma_ --server="http://localhost:9955" --scriptpath=/mediawiki/ --lang=en --pass=mysqlmysql "MA Copy" "Admin"
php /var/www/html/mediawiki/maintenance/importDump.php --no-updates --namespaces "0|10|14" --conf /var/www/html/mediawiki/LocalSettings.php /tmp/ma/enmemoryalpha_pages_current.xml
php /var/www/html/mediawiki/maintenance/rebuildall.php
while true ; do
  sleep 1
done