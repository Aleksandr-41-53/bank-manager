#!/usr/bin/env bash

mvn clean package

echo 'Copy files...';

scp -i ~/.ssh/id_rsa \
    build/libs/bank-manager-0.0.1-SNAPSHOT.jar \
    jaxx@pc-media:/home/jaxx/web/bank-manager/public

echo 'Restart server...';


# stop java
# run background & wright log
ssh jaxx@pc-media << EOF

cd web/bank-manager/public/
pgrep java | xargs kill -9
nohup java -jar bank-manager-1.0-SNAPSHOT.jar > ../logs/bank-manager.log &

EOF

echo 'Bye!'
