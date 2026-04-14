# 1. выдача прав админу, брокерам (внутри кластера) и на кластер для продюсера
docker exec -it kafka1 /bin/kafka-acls --bootstrap-server kafka1:29092 --add --allow-principal User:CN=kafka1,L=Moscow,OU=Practice,O=Yandex,C=RU --operation All --cluster --command-config /tmp/adminclient-configs.conf
docker exec -it kafka1 /bin/kafka-acls --bootstrap-server kafka1:29092 --add --allow-principal User:CN=kafka2,L=Moscow,OU=Practice,O=Yandex,C=RU --operation All --cluster --command-config /tmp/adminclient-configs.conf
docker exec -it kafka1 /bin/kafka-acls --bootstrap-server kafka1:29092 --add --allow-principal User:CN=kafka3,L=Moscow,OU=Practice,O=Yandex,C=RU --operation All --cluster --command-config /tmp/adminclient-configs.conf
docker exec -it kafka4 /bin/kafka-acls --bootstrap-server kafka4:29095 --add --allow-principal User:CN=kafka4,L=Moscow,OU=Practice,O=Yandex,C=RU --operation All --cluster --command-config /tmp/adminclient-configs.conf
docker exec -it kafka4 /bin/kafka-acls --bootstrap-server kafka4:29095 --add --allow-principal User:CN=kafka5,L=Moscow,OU=Practice,O=Yandex,C=RU --operation All --cluster --command-config /tmp/adminclient-configs.conf
docker exec -it kafka4 /bin/kafka-acls --bootstrap-server kafka4:29095 --add --allow-principal User:CN=kafka6,L=Moscow,OU=Practice,O=Yandex,C=RU --operation All --cluster --command-config /tmp/adminclient-configs.conf

docker exec -it kafka1 /bin/kafka-acls --bootstrap-server kafka1:29092 --add --allow-principal User:CN=admin,L=Moscow,OU=Practice,O=Yandex,C=RU --operation All --cluster --command-config /tmp/adminclient-configs.conf
docker exec -it kafka4 /bin/kafka-acls --bootstrap-server kafka4:29095 --add --allow-principal User:CN=admin,L=Moscow,OU=Practice,O=Yandex,C=RU --operation All --cluster --command-config /tmp/adminclient-configs.conf

docker exec -it kafka-0 /bin/kafka-acls --bootstrap-server kafka-0:29092 --add --allow-principal User:CN=producer,L=Moscow,OU=Practice,O=Yandex,C=RU --operation ClusterAction --operation Describe --cluster --command-config /tmp/adminclient-configs.conf

# 2.1. создание топиков для первого кластера
docker exec -it kafka1 /bin/kafka-topics --bootstrap-server kafka1:29092 --create --topic product-topic --partitions 3 --replication-factor 3 --command-config /tmp/adminclient-configs.conf
docker exec -it kafka1 /bin/kafka-topics --bootstrap-server kafka1:29092 --create --topic client-events-topic --partitions 3 --replication-factor 3 --command-config /tmp/adminclient-configs.conf
docker exec -it kafka1 /bin/kafka-topics --bootstrap-server kafka1:29092 --create --topic filter-product-topic --partitions 3 --replication-factor 3 --command-config /tmp/adminclient-configs.conf
docker exec -it kafka1 /bin/kafka-topics --bootstrap-server kafka1:29092 --create --topic blacklist-words-topic --partitions 3 --replication-factor 3 --command-config /tmp/adminclient-configs.conf
# 2.2. создание топиков для второго кластера
docker exec -it kafka4 /bin/kafka-topics --bootstrap-server kafka4:29095 --create --topic product-topic --partitions 3 --replication-factor 3 --command-config /tmp/adminclient-configs.conf
docker exec -it kafka4 /bin/kafka-topics --bootstrap-server kafka4:29095 --create --topic client-events-topic --partitions 3 --replication-factor 3 --command-config /tmp/adminclient-configs.conf
docker exec -it kafka4 /bin/kafka-topics --bootstrap-server kafka4:29095 --create --topic filter-product-topic --partitions 3 --replication-factor 3 --command-config /tmp/adminclient-configs.conf
docker exec -it kafka4 /bin/kafka-topics --bootstrap-server kafka4:29095 --create --topic blacklist-words-topic --partitions 3 --replication-factor 3 --command-config /tmp/adminclient-configs.conf
docker exec -it kafka4 /bin/kafka-topics --bootstrap-server kafka4:29095 --create --topic recommendation-product-topic --partitions 3 --replication-factor 3 --command-config /tmp/adminclient-configs.conf

# 3. выдача прав продюсерам и консьюмерам на топики
docker exec -it kafka-0 /bin/kafka-acls --bootstrap-server kafka-0:29092 --add --allow-principal User:CN=producer,L=Moscow,OU=Practice,O=Yandex,C=RU --operation WRITE --operation DESCRIBE --topic topic-1 --command-config /tmp/adminclient-configs.conf
docker exec -it kafka-0 /bin/kafka-acls --bootstrap-server kafka-0:29092 --add --allow-principal User:CN=producer,L=Moscow,OU=Practice,O=Yandex,C=RU --operation WRITE --operation DESCRIBE --topic topic-2 --command-config /tmp/adminclient-configs.conf
docker exec -it kafka-0 /bin/kafka-acls --bootstrap-server kafka-0:29092 --add --allow-principal User:CN=consumer,L=Moscow,OU=Practice,O=Yandex,C=RU --operation READ --operation DESCRIBE --topic topic-1 --command-config /tmp/adminclient-configs.conf


# список пользователей
# только для модуля shop-service и первого кластера
shop-service - 1 сертификат
client-service - 2 сертификат
blacklist-service - 1 сертификат
analytics-service - 1 сертификат
mm2 - 1 сертификат
es - 1 сертификат

