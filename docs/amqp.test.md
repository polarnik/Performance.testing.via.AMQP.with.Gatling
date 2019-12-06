Запуск инфраструктуры в Docker
------------------------------------

### Запуск InfluxDB в Docker

https://hub.docker.com/_/influxdb

```

docker network create loadlab

docker pull influxdb:1.7.9

cd $PROJECT

mkdir -p /tmp/var/lib/influxdb

docker run --name=influxdb \
    --network=loadlab \
    -p 8086:8086 -p 2003:2003 -p 2004:2004 \
    -v $PWD/infrastructure/influxdb/influxdb.conf:/etc/influxdb/influxdb.conf:ro \
    -v /tmp/var/lib/influxdb:/var/lib/influxdb \
    influxdb:1.7.9 -config /etc/influxdb/influxdb.conf

```

Запуск клиента InfluxDB

1. Посмотреть идентификатор запущенного контейнера
```
docker ps
```
Будет показано что-то подобное (контейнер с NAMES influxdb):
```
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS                                                      NAMES
c4fdc2f67da2        influxdb:1.7.9      "/entrypoint.sh -con…"   12 seconds ago      Up 9 seconds        0.0.0.0:2003-2004->2003-2004/tcp, 0.0.0.0:8086->8086/tcp   influxdb
```
2. Запустить клиента influx указав имя конфтейнера influxdb
```
docker exec -it influxdb influx
```
3. Посмотреть какие базы данных есть в influxdb:
```
> show databases
```
4. Создать базы данных:
* jmeter - для Apache.JMeter
* gatling - для Gatling
* telegraf - для Telegraf
* sjk - для SJK
```
create database jmeter
create database gatling
create database telegraf
create database sjk
```

### Запуск Grafana в Docker

https://hub.docker.com/r/grafana/grafana

```
docker pull grafana/grafana:6.5.0

cd $PROJECT

mkdir -p /tmp/var/lib/grafana
mkdir -p /tmp/usr/share/grafana
mkdir -p /tmp/var/log/grafana
mkdir -p /tmp/var/lib/grafana/plugins

ID=$(id -u) # saves your user id in the ID variable

docker run \
  --network=loadlab \
  --user $ID \
  -p 3000:3000 \
  --name=grafana \
  -e "GF_INSTALL_PLUGINS=grafana-clock-panel,grafana-simple-json-datasource,novalabs-annotations-panel,yesoreyeram-boomtheme-panel,mtanda-histogram-panel,natel-influx-admin-panel" \
  -v $PWD/infrastructure/grafana/grafana.ini:/etc/grafana/grafana.ini \
  -v "/tmp/var/lib/grafana:/var/lib/grafana" \
  -v "/tmp/usr/share/grafana:/var/usr/share/grafana" \
  -v "/tmp/var/log/grafana:/var/log/grafana" \
  -v "/tmp/var/lib/grafana/plugins:/var/lib/grafana/plugins" \
  -v "$PWD/infrastructure/grafana/provisioning:/etc/grafana/provisioning" \
  grafana/grafana:6.5.1
```


### Запуск IBM.MQ в Docker

https://github.com/ibm-messaging/mq-container/blob/master/docs/usage.md

```
docker volume create qm1data

docker run \
  --name=ibmmq \
  --network=loadlab \
  --env LICENSE=accept \
  --env MQ_QMGR_NAME=QM1 \
  --env MQ_ENABLE_METRICS=true \
  --publish 1414:1414 \
  --publish 9443:9443 \
  --publish 9157:9157 \
  --volume qm1data:/mnt/mqm \
  ibmcom/mq:9.1.3.0
```

Копирование клиентских библиотек из контейнера в каталог
```
cd $PROJECT/libs
./get.ibm.mq.libs.from.docker.sh
```

https://github.com/ibm-messaging/mq-container/blob/master/docs/developer-config.md

Будет доступна админка:

https://127.0.0.1:9443/ibmmq/console/
admin/passw0rd

The following users are created:

User admin for administration (in the mqm group). Default password is passw0rd.
User app for messaging (in a group called mqclient). No password by default.
Users in mqclient group have been given access connect to all queues and topics starting with DEV.** and have put, get, pub, sub, browse and inq permissions.

The following queues and topics are created:

DEV.QUEUE.1
DEV.QUEUE.2
DEV.QUEUE.3
DEV.DEAD.LETTER.QUEUE - configured as the Queue Manager's Dead Letter Queue.
DEV.BASE.TOPIC - uses a topic string of dev/.
Two channels are created, one for administration, the other for normal messaging:

DEV.ADMIN.SVRCONN - configured to only allow the admin user to connect into it. A user and password must be supplied.
DEV.APP.SVRCONN - does not allow administrative users to connect. Password is optional unless you choose a password for app users.
A new listener is created (the SYSTEM listener is fine, but system objects are not shown by default in the web console):

DEV.LISTENER.TCP - listens on port 1414.

### Запуск RabbitMQ в Docker

```

cd $PROJECT

mkdir -p /tmp/var/lib/rabbitmq

ID=$(id -u) # saves your user id in the ID variable

docker run \
    --hostname rabbitmq \
    --user $ID \
    --name rabbitmq \
    --network=loadlab \
    -v /tmp/var/lib/rabbitmq:/var/lib/rabbitmq \
    -v $PWD/infrastructure/rabbitmq/rabbitmq.conf:/etc/rabbitmq/rabbitmq.conf \
    --publish 5672:5672 \
    --publish 15672:15672 \
    rabbitmq:3.8.1-management
```


Apache.JMeter-эмулятор сервера нагружаемого по AMQP 
-------------------------------------------------------

### Сборка AMQP-плагина zeph1rus для Apache.JMeter


Тест RequestReply с быстрыми ответами и фиксированными очередями в RabbitMQ
----------------------------------------------------------------------

### Запуск сервера Apache.JMeter

### Запуск нагрузочного теста Gatling

Тест RequestReply с долгими ответами и фиксированными очередями в RabbitMQ
----------------------------------------------------------------------

### Запуск сервера Apache.JMeter

### Запуск нагрузочного теста Gatling


Тест с аггрегацией запросов и фиксированными очередями в RabbitMQ
-----------------------------------------------------------------

### Запуск сервера Apache.JMeter

### Запуск нагрузочного теста Gatling