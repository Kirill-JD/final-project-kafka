# Финальный проект

## Структура проекта
- `apps` - все модули на java/spring boot
  - `analytics-service` - модуль для аналитики (работа с hadoop и spark). Наполнение топика рекомендаций во втором кластере
  - `blacklist-service` - модуль для фильтрации продуктов по запрещённым названиям (работа с KStream и GlobalKTable)
  - `client-service` - эмулятор API для взаимодействия с пользователями (поиск товаров / выдача рекомендаций)
  - `shop-service` - эмулятор API для наполнения топика продуктов (наполнение из файлов в формате json)
- `certs` - каталог сертификатов для работы брокеров
- `infrastructure` - все вспомогательные файлы для работы приложения (метрики, алерты, запросы для коннекторов итд)
- `libs` - общие вспомогательные модули для работы приложения (по сути только Avro схемы и кодген при билде)
- `scripts` - команды настройки доступов для участников взаимодействия с приложением

## Используемые технологии
- Zookeeper / Kafka / Kafka-Connect / KStream / MM2 / SchemaRegistry / Avro
- Prometheus / Grafana
- ElasticSearch
- Hadoop (Hdfs) / Spark

## Запуск приложения
1) Сбилдить проект 1 раз `gradle bootJar` для дальнейшей корректной работы (скорее всего понадобится proxy или vpn на этот шаг, лично у меня без этого с https://packages.confluent.io/maven/ ничего не подтягивалось)
2) запустить `docker-compose.yaml`
3) выполнить команды из файла `scripts/commands` (ВАЖНО! Я делал на винде с wsl, команды могут чуток отличаться, надо подогнать под свою ОС)
4) выставить в .env `KAFKA_ALLOW_EVERYONE_IF_NO_ACL_FOUND=false`
5) перезапустить `docker-compose.yaml`
6) выполнить запросы для коннекторов из `infrastructure/kafka-connect/script` и `infrastructure/mm2/script`
Пример:
```curl
curl --location --request PUT 'http://localhost:8083/connectors/es-sink-filtered-products/config' `
--header 'Content-Type: application/json' `
--data '{
  "connector.class": "io.confluent.connect.elasticsearch.ElasticsearchSinkConnector",
  "tasks.max": "1",
  "topics": "filter-product-topic",
  "connection.url": "http://elasticsearch:9200",
  "type.name": "_doc",
  "key.ignore": "true",
  "schema.ignore": "true",
  "behavior.on.null.values": "ignore",
  "write.method": "upsert",
  "value.converter": "io.confluent.connect.avro.AvroConverter",
  "key.converter": "org.apache.kafka.connect.storage.StringConverter",
  "value.converter.schema.registry.url": "http://schema-registry:8081"
}' 
```
```curl
curl --location --request PUT 'http://localhost:8084/connectors/mirror2/config' `
--header 'Content-Type: application/json' `
--data '{
  "name": "mirror2",
  "connector.class": "org.apache.kafka.connect.mirror.MirrorSourceConnector",
  "source.cluster.alias": "source",
  "topics.regex": ".*",
  "source.cluster.bootstrap.servers": "kafka1:29092,kafka2:29093,kafka3:29094",
  "target.cluster.bootstrap.servers": "kafka4:29095,kafka5:29096,kafka6:29097",
  "producer.override.bootstrap.servers": "kafka4:29095,kafka5:29096,kafka6:29097",
  "offset-syncs.topic.replication.factor": "2",
  "refresh.topics.interval.seconds": "30",
  "replication.policy.class": "org.apache.kafka.connect.mirror.IdentityReplicationPolicy",
  "sync.topic.acls.enabled": "false",
  "source.cluster.security.protocol": "SSL",
  "source.cluster.ssl.truststore.location": "/etc/kafka/certs/mm2.truststore.jks",
  "source.cluster.ssl.truststore.password": "password-mm2",
  "source.cluster.ssl.keystore.location": "/etc/kafka/certs/mm2.keystore.jks",
  "source.cluster.ssl.keystore.password": "password-mm2",
  "source.cluster.ssl.key.password": "password-mm2",

  "target.cluster.security.protocol": "SSL",
  "target.cluster.ssl.truststore.location": "/etc/kafka/certs/mm2.truststore.jks",
  "target.cluster.ssl.truststore.password": "password-mm2",
  "target.cluster.ssl.keystore.location": "/etc/kafka/certs/mm2.keystore.jks",
  "target.cluster.ssl.keystore.password": "password-mm2",
  "target.cluster.ssl.key.password": "password-mm2"
}'
```

#### ВАЖНО!!! (пройтись по конфигам для `blacklist-service` и `client-service` и поправить пути до сертификатов)
7) запустить `shop-service`
8) запустить `blacklist-service`
9) раскомментировать в `docker-compose.yaml` блок для `analytics-service` и запустить в докере (примечание: не удалось нормально реализовать для локального запуска, т.к. один url hdfs использовался как для внутренней сетки докера, так и для внешнего доступа)
10) запустить `client-service`

### Архитектура и краткое описание логики приложения
Рассмотрим процесс от залива товара магазином, до получения его конечным пользователем:
- модуль `shop-service` - эмулятор API для магазинов, которые выставляют свои продукты. Вычитывает данные из файлов и отправляет в kafka (1 кластер) для добавления товаров, например в БД, для дальнейшего использования;
- модуль `blacklist-service` - фильтр по продуктам. Добавляет возможность отсеять какие-либо товары до их попадания в БД;
- `kafka-connector` - перекладывает отфильтрованные ранее продукты в БД (в рамках проекта был выбран elasticSearch);
- `mm2` - коннектор который параллельно со всеми процессами в режиме real-time копирует все данные из 1-ого кластера во 2-ой для обеспечения отказоустойчивости приложения
- модуль `client-service` - эмулятор API для клиентов, позволяет выполнить поиск продуктов (по БД elasticSearch), а также получить персональные рекомендации продуктов из 2-ого кластера после обработки модулем `analytics-service` (также собирает информацию о действиях пользователя и записывает в 1-ый кластер)
- модуль `analyics-service` - обрабатывает информацию из 2-ого кластера с использованием hadoop/spark для составления рекомендаций (логики как таковой нет, просто фильтр по полю из конфига модуля)
- все брокеры используют TLS/ACL и дают доступ для модулей только к необходимым им для работы топикам
- для всех брокеров настроены метрики (дашборд для grafana выгружен с официального сайта https://grafana.com/grafana/dashboards/7589-kafka-exporter-overview/)
