===================================================
# 1. выдача прав брокерам (внутри кластера) и на кластер для продюсера
docker exec -it kafka1 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-acls --bootstrap-server kafka1:29092 --add --allow-principal User:CN=kafka1,L=Moscow,OU=Practice,O=Yandex,C=RU --operation All --cluster --command-config /tmp/adminclient-configs.conf"
docker exec -it kafka1 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-acls --bootstrap-server kafka1:29092 --add --allow-principal User:CN=kafka2,L=Moscow,OU=Practice,O=Yandex,C=RU --operation All --cluster --command-config /tmp/adminclient-configs.conf"
docker exec -it kafka1 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-acls --bootstrap-server kafka1:29092 --add --allow-principal User:CN=kafka3,L=Moscow,OU=Practice,O=Yandex,C=RU --operation All --cluster --command-config /tmp/adminclient-configs.conf"
===================================================

#docker exec -it kafka1 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-acls --bootstrap-server kafka1:29092 --add --allow-principal User:CN=schema-registry,L=Moscow,OU=Practice,O=Yandex,C=RU --operation All --cluster --command-config /tmp/adminclient-configs.conf"

docker exec -it kafka4 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-acls --bootstrap-server kafka4:29095 --add --allow-principal User:CN=kafka4,L=Moscow,OU=Practice,O=Yandex,C=RU --operation All --cluster --command-config /tmp/adminclient-configs.conf"
docker exec -it kafka4 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-acls --bootstrap-server kafka4:29095 --add --allow-principal User:CN=kafka5,L=Moscow,OU=Practice,O=Yandex,C=RU --operation All --cluster --command-config /tmp/adminclient-configs.conf"
docker exec -it kafka4 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-acls --bootstrap-server kafka4:29095 --add --allow-principal User:CN=kafka6,L=Moscow,OU=Practice,O=Yandex,C=RU --operation All --cluster --command-config /tmp/adminclient-configs.conf"


===================================================
docker exec -it kafka1 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-acls --bootstrap-server kafka1:29092 --add --allow-principal User:CN=shop-service,L=Moscow,OU=Practice,O=Yandex,C=RU --operation ClusterAction --operation Describe --cluster --command-config /tmp/adminclient-configs.conf"
===================================================

===================================================
# 2.1. создание топиков для первого кластера
docker exec -it kafka1 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-topics --bootstrap-server kafka1:29092 --create --topic product-topic --partitions 3 --replication-factor 3 --command-config /tmp/adminclient-configs.conf"
docker exec -it kafka1 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-topics --bootstrap-server kafka1:29092 --create --topic client-events-topic --partitions 3 --replication-factor 3 --command-config /tmp/adminclient-configs.conf"
docker exec -it kafka1 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-topics --bootstrap-server kafka1:29092 --create --topic filter-product-topic --partitions 3 --replication-factor 3 --command-config /tmp/adminclient-configs.conf"
docker exec -it kafka1 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-topics --bootstrap-server kafka1:29092 --create --topic blacklist-words-topic --partitions 3 --replication-factor 3 --command-config /tmp/adminclient-configs.conf"
===================================================

# 2.2. создание топиков для второго кластера
docker exec -it kafka4 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-topics --bootstrap-server kafka4:29095 --create --topic product-topic --partitions 3 --replication-factor 3 --command-config /tmp/adminclient-configs.conf"
docker exec -it kafka4 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-topics --bootstrap-server kafka4:29095 --create --topic client-events-topic --partitions 3 --replication-factor 3 --command-config /tmp/adminclient-configs.conf"
docker exec -it kafka4 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-topics --bootstrap-server kafka4:29095 --create --topic filter-product-topic --partitions 3 --replication-factor 3 --command-config /tmp/adminclient-configs.conf"
docker exec -it kafka4 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-topics --bootstrap-server kafka4:29095 --create --topic blacklist-words-topic --partitions 3 --replication-factor 3 --command-config /tmp/adminclient-configs.conf"
docker exec -it kafka4 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-topics --bootstrap-server kafka4:29095 --create --topic recommendation-product-topic --partitions 3 --replication-factor 3 --command-config /tmp/adminclient-configs.conf"



===================================================
# 3. выдача прав продюсерам и консьюмерам на топики
docker exec -it kafka1 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-acls --bootstrap-server kafka1:29092 --add --allow-principal User:CN=shop-service,L=Moscow,OU=Practice,O=Yandex,C=RU --operation WRITE --operation DESCRIBE --topic product-topic --command-config /tmp/adminclient-configs.conf"
docker exec -it kafka1 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-acls --bootstrap-server kafka1:29092 --add --allow-principal User:CN=ui,L=Moscow,OU=Practice,O=Yandex,C=RU --operation ALL --topic product-topic --command-config /tmp/adminclient-configs.conf"
===================================================

docker exec -it kafka-0 /bin/kafka-acls --bootstrap-server kafka-0:29092 --add --allow-principal User:CN=producer,L=Moscow,OU=Practice,O=Yandex,C=RU --operation WRITE --operation DESCRIBE --topic topic-2 --command-config /tmp/adminclient-configs.conf
docker exec -it kafka-0 /bin/kafka-acls --bootstrap-server kafka-0:29092 --add --allow-principal User:CN=consumer,L=Moscow,OU=Practice,O=Yandex,C=RU --operation READ --operation DESCRIBE --topic topic-1 --command-config /tmp/adminclient-configs.conf


# список пользователей
# только для модуля shop-service и первого кластера
6 сертификатов на каждый брокер kafka
shop-service - 1 сертификат
client-service - 2 сертификат
blacklist-service - 1 сертификат
analytics-service - 1 сертификат
mm2 - 1 сертификат
es - 1 сертификат

docker exec -it schema-registry bashkafka-broker-api-versions --bootstrap-server kafka1:29092

docker exec -it kafka1 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-broker-api-versions --bootstrap-server kafka1:29092 --command-config /tmp/adminclient-configs.conf"








========================================================================================================================
============================================ Итоговые команды ==========================================================
========================================================================================================================
# 1. выдача прав брокерам (внутри кластеров), kafka-ui, mm2, kafka-connect и на кластер для продюсера
docker exec -it kafka1 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-acls --bootstrap-server kafka1:29092 --add --allow-principal User:CN=kafka1,L=Moscow,OU=Practice,O=Yandex,C=RU --operation All --cluster --command-config /tmp/adminclient-configs.conf"
docker exec -it kafka1 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-acls --bootstrap-server kafka1:29092 --add --allow-principal User:CN=kafka2,L=Moscow,OU=Practice,O=Yandex,C=RU --operation All --cluster --command-config /tmp/adminclient-configs.conf"
docker exec -it kafka1 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-acls --bootstrap-server kafka1:29092 --add --allow-principal User:CN=kafka3,L=Moscow,OU=Practice,O=Yandex,C=RU --operation All --cluster --command-config /tmp/adminclient-configs.conf"

docker exec -it kafka4 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-acls --bootstrap-server kafka4:29095 --add --allow-principal User:CN=kafka4,L=Moscow,OU=Practice,O=Yandex,C=RU --operation All --cluster --command-config /tmp/adminclient-configs.conf"
docker exec -it kafka4 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-acls --bootstrap-server kafka4:29095 --add --allow-principal User:CN=kafka5,L=Moscow,OU=Practice,O=Yandex,C=RU --operation All --cluster --command-config /tmp/adminclient-configs.conf"
docker exec -it kafka4 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-acls --bootstrap-server kafka4:29095 --add --allow-principal User:CN=kafka6,L=Moscow,OU=Practice,O=Yandex,C=RU --operation All --cluster --command-config /tmp/adminclient-configs.conf"

docker exec -it kafka1 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-acls --bootstrap-server kafka1:29092 --add --allow-principal User:CN=ui,L=Moscow,OU=Practice,O=Yandex,C=RU --operation ALL --cluster --command-config /tmp/adminclient-configs.conf"
docker exec -it kafka4 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-acls --bootstrap-server kafka4:29095 --add --allow-principal User:CN=ui,L=Moscow,OU=Practice,O=Yandex,C=RU --operation ALL --cluster --command-config /tmp/adminclient-configs.conf"

docker exec -it kafka1 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-acls --bootstrap-server kafka1:29092 --add --allow-principal User:CN=mm2,L=Moscow,OU=Practice,O=Yandex,C=RU --operation ALL --cluster --command-config /tmp/adminclient-configs.conf"
docker exec -it kafka4 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-acls --bootstrap-server kafka4:29095 --add --allow-principal User:CN=mm2,L=Moscow,OU=Practice,O=Yandex,C=RU --operation ALL --cluster --command-config /tmp/adminclient-configs.conf"

docker exec -it kafka1 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-acls --bootstrap-server kafka1:29092 --add --allow-principal User:CN=shop-service,L=Moscow,OU=Practice,O=Yandex,C=RU --operation ClusterAction --operation Describe --cluster --command-config /tmp/adminclient-configs.conf"
docker exec -it kafka1 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-acls --bootstrap-server kafka1:29092 --add --allow-principal User:CN=kafka-connect,L=Moscow,OU=Practice,O=Yandex,C=RU --operation ClusterAction --operation Describe --cluster --command-config /tmp/adminclient-configs.conf"
docker exec -it kafka1 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-acls --bootstrap-server kafka1:29092 --add --allow-principal User:CN=client-service-producer,L=Moscow,OU=Practice,O=Yandex,C=RU --operation ClusterAction --operation Describe --cluster --command-config /tmp/adminclient-configs.conf"
docker exec -it kafka4 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-acls --bootstrap-server kafka4:29095 --add --allow-principal User:CN=client-service-consumer,L=Moscow,OU=Practice,O=Yandex,C=RU --operation ClusterAction --operation Describe --cluster --command-config /tmp/adminclient-configs.conf"


# 2.1. создание топиков для первого кластера
docker exec -it kafka1 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-topics --bootstrap-server kafka1:29092 --create --topic product-topic --partitions 3 --replication-factor 3 --command-config /tmp/adminclient-configs.conf"
docker exec -it kafka1 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-topics --bootstrap-server kafka1:29092 --create --topic client-events-topic --partitions 3 --replication-factor 3 --command-config /tmp/adminclient-configs.conf"
docker exec -it kafka1 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-topics --bootstrap-server kafka1:29092 --create --topic filter-product-topic --partitions 3 --replication-factor 3 --command-config /tmp/adminclient-configs.conf"
docker exec -it kafka1 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-topics --bootstrap-server kafka1:29092 --create --topic blacklist-words-topic --partitions 3 --replication-factor 3 --command-config /tmp/adminclient-configs.conf"

# 2.1. создание топиков для второго кластера
docker exec -it kafka4 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-topics --bootstrap-server kafka4:29095 --create --topic product-topic --partitions 3 --replication-factor 3 --command-config /tmp/adminclient-configs.conf"
docker exec -it kafka4 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-topics --bootstrap-server kafka4:29095 --create --topic client-events-topic --partitions 3 --replication-factor 3 --command-config /tmp/adminclient-configs.conf"
docker exec -it kafka4 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-topics --bootstrap-server kafka4:29095 --create --topic filter-product-topic --partitions 3 --replication-factor 3 --command-config /tmp/adminclient-configs.conf"
docker exec -it kafka4 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-topics --bootstrap-server kafka4:29095 --create --topic blacklist-words-topic --partitions 3 --replication-factor 3 --command-config /tmp/adminclient-configs.conf"
docker exec -it kafka4 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-topics --bootstrap-server kafka4:29095 --create --topic recommendation-product-topic --partitions 3 --replication-factor 3 --command-config /tmp/adminclient-configs.conf"


# 3. выдача прав продюсерам и консьюмерам на топики
# 3.0 выдача прав для ui и schemaRegistry
docker exec -it kafka1 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-acls --bootstrap-server kafka1:29092 --add --allow-principal User:CN=ui,L=Moscow,OU=Practice,O=Yandex,C=RU --operation ALL --topic '*' --command-config /tmp/adminclient-configs.conf"
docker exec -it kafka4 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-acls --bootstrap-server kafka4:29095 --add --allow-principal User:CN=ui,L=Moscow,OU=Practice,O=Yandex,C=RU --operation ALL --topic '*' --command-config /tmp/adminclient-configs.conf"
docker exec -it kafka1 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-acls --bootstrap-server kafka1:29092 --add --allow-principal User:CN=schema-registry,L=Moscow,OU=Practice,O=Yandex,C=RU --operation ALL --topic _schemas --command-config /tmp/adminclient-configs.conf"
docker exec -it kafka4 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-acls --bootstrap-server kafka4:29095 --add --allow-principal User:CN=schema-registry,L=Moscow,OU=Practice,O=Yandex,C=RU --operation ALL --topic _schemas --command-config /tmp/adminclient-configs.conf"

# 3.1. права для shop-service
docker exec -it kafka1 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-acls --bootstrap-server kafka1:29092 --add --allow-principal User:CN=shop-service,L=Moscow,OU=Practice,O=Yandex,C=RU --operation WRITE --operation DESCRIBE --topic product-topic --command-config /tmp/adminclient-configs.conf"

# 3.2. права для blacklist-service
docker exec -it kafka1 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-acls --bootstrap-server kafka1:29092 --add --allow-principal User:CN=blacklist-service,L=Moscow,OU=Practice,O=Yandex,C=RU --operation READ --operation DESCRIBE --topic product-topic --command-config /tmp/adminclient-configs.conf"
docker exec -it kafka1 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-acls --bootstrap-server kafka1:29092 --add --allow-principal User:CN=blacklist-service,L=Moscow,OU=Practice,O=Yandex,C=RU --operation WRITE --operation DESCRIBE --topic filter-product-topic --command-config /tmp/adminclient-configs.conf"
docker exec -it kafka1 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-acls --bootstrap-server kafka1:29092 --add --allow-principal User:CN=blacklist-service,L=Moscow,OU=Practice,O=Yandex,C=RU --operation READ --operation WRITE --operation DESCRIBE --topic blacklist-words-topic --command-config /tmp/adminclient-configs.conf"

# 3.3. права для client-service
docker exec -it kafka1 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-acls --bootstrap-server kafka1:29092 --add --allow-principal User:CN=client-service-producer,L=Moscow,OU=Practice,O=Yandex,C=RU --operation WRITE --operation DESCRIBE --topic client-events-topic --command-config /tmp/adminclient-configs.conf"
docker exec -it kafka4 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-acls --bootstrap-server kafka4:29095 --add --allow-principal User:CN=client-service-consumer,L=Moscow,OU=Practice,O=Yandex,C=RU --operation READ --operation DESCRIBE --topic recommendation-product-topic --command-config /tmp/adminclient-configs.conf"

# 3.4. права для analytics-service


# 3.5. права для kafka-connect
docker exec -it kafka1 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-acls --bootstrap-server kafka1:29092 --add --allow-principal User:CN=kafka-connect,L=Moscow,OU=Practice,O=Yandex,C=RU --operation READ --operation DESCRIBE --topic filter-product-topic --command-config /tmp/adminclient-configs.conf"
docker exec -it kafka1 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-acls --bootstrap-server kafka1:29092 --add --allow-principal User:CN=kafka-connect,L=Moscow,OU=Practice,O=Yandex,C=RU --operation CREATE --operation READ --operation WRITE --operation DESCRIBE --topic connect-config-storage --command-config /tmp/adminclient-configs.conf"
docker exec -it kafka1 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-acls --bootstrap-server kafka1:29092 --add --allow-principal User:CN=kafka-connect,L=Moscow,OU=Practice,O=Yandex,C=RU --operation CREATE --operation READ --operation WRITE --operation DESCRIBE --topic connect-offset-storage --command-config /tmp/adminclient-configs.conf"
docker exec -it kafka1 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-acls --bootstrap-server kafka1:29092 --add --allow-principal User:CN=kafka-connect,L=Moscow,OU=Practice,O=Yandex,C=RU --operation CREATE --operation READ --operation WRITE --operation DESCRIBE --topic connect-status-storage --command-config /tmp/adminclient-configs.conf"

# 3.6. права для mm2
docker exec -it kafka1 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-acls --bootstrap-server kafka1:29092 --add --allow-principal User:CN=mm2,L=Moscow,OU=Practice,O=Yandex,C=RU --operation CREATE --operation READ --operation WRITE --operation DESCRIBE --operation DescribeConfigs --topic '*' --command-config /tmp/adminclient-configs.conf"
docker exec -it kafka4 bash -c "export KAFKA_OPTS='' && export KAFKA_JMX_OPTS='' && /bin/kafka-acls --bootstrap-server kafka4:29095 --add --allow-principal User:CN=mm2,L=Moscow,OU=Practice,O=Yandex,C=RU --operation CREATE --operation READ --operation WRITE --operation DESCRIBE --operation DescribeConfigs --topic '*' --command-config /tmp/adminclient-configs.conf"



