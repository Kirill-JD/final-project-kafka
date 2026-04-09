object Versions {
    const val jvm = 17
    const val springBoot = "3.4.5"

    const val avro = "1.11.3"
    const val elasticsearch = "7.15.2"
    const val jackson = "2.17.2"
    const val hadoopClient = "3.3.6"
    const val kafka = "3.9.1"
    const val kafkaAvroSerializer = "7.6.0"
    const val lombok = "1.18.38"
    const val mapstruct = "1.5.5.Final"
    const val picocli = "4.7.4"
    const val spark = "3.5.1"
}

object Deps {

    object Spring {
        const val springBootStarter = "org.springframework.boot:spring-boot-starter"
        const val springBootStarterDataElasticsearch = "org.springframework.boot:spring-boot-starter-data-elasticsearch"
        const val springKafka = "org.springframework.kafka:spring-kafka"
    }

    object Other {
        const val avro = "org.apache.avro:avro:${Versions.avro}"
        const val elasticsearch = "org.elasticsearch.client:elasticsearch-rest-high-level-client:${Versions.elasticsearch}"
        const val hadoopClient = "org.apache.hadoop:hadoop-client:${Versions.hadoopClient}"
        const val jacksonAnnotations = "com.fasterxml.jackson.core:jackson-annotations:${Versions.jackson}"
        const val jacksonDatabind = "com.fasterxml.jackson.core:jackson-databind:${Versions.jackson}"
        const val jacksonDatatypeJsr310 = "com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${Versions.jackson}"
        const val kafkaStreams = "org.apache.kafka:kafka-streams:${Versions.kafka}"
        const val kafkaAvroSerializer ="io.confluent:kafka-avro-serializer:${Versions.kafkaAvroSerializer}"
        const val kafkaSchemaRegistryClient ="io.confluent:kafka-schema-registry-client:${Versions.kafkaAvroSerializer}"
        const val kafkaStreamsAvroSerde = "io.confluent:kafka-streams-avro-serde:${Versions.kafkaAvroSerializer}"
        const val lombok = "org.projectlombok:lombok:${Versions.lombok}"
        const val mapstruct = "org.mapstruct:mapstruct:${Versions.mapstruct}"
        const val mapstructProcessor = "org.mapstruct:mapstruct-processor:${Versions.mapstruct}"
        const val picocli = "info.picocli:picocli-spring-boot-starter:${Versions.picocli}"
        const val sparkAvro = "org.apache.spark:spark-avro_2.12:${Versions.spark}"
        const val sparkSql = "org.apache.spark:spark-sql_2.12:${Versions.spark}"
        const val sparkSqlKafka = "org.apache.spark:spark-sql-kafka-0-10_2.12:${Versions.spark}"
    }
}