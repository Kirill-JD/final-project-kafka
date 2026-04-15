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
1) запустить `docker-compose.yaml`
2) выполнить команды из файла `scripts/commands`
3) выставить в .env `KAFKA_ALLOW_EVERYONE_IF_NO_ACL_FOUND=false`
4) перезапустить `docker-compose.yaml`
5) выполнить запросы для коннекторов из `infrastructure/kafka-connect/script` и `infrastructure/mm2/script`
#### ВАЖНО!!! (пройтись по конфигам для `blacklist-service` и `client-service` и поправить пути до сертификатов)
6) запустить `shop-service`
7) запустить `blacklist-service`
8) раскомментировать в `docker-compose.yaml` блок для `analytics-service` и запустить
9) запустить `shop-service`

### Архитектура и краткое описание логики приложения
