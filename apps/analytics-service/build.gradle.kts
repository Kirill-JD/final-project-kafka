plugins {
    id("org.springframework.boot") version Versions.springBoot
    id("io.spring.dependency-management") version "1.1.7"
}

dependencies {
    compileOnly(Deps.Other.lombok)
    annotationProcessor(Deps.Other.lombok)

    implementation(Deps.Spring.springBootStarter) {
        exclude(group = "org.apache.logging.log4j")
    }
    implementation(Deps.Spring.springKafka)

    implementation(Deps.Other.hadoopClient)
    implementation(Deps.Other.kafkaAvroSerializer)
    implementation(Deps.Other.kafkaSchemaRegistryClient)
    implementation(Deps.Other.sparkAvro)
    implementation(Deps.Other.sparkSql)
    implementation(Deps.Other.sparkSqlKafka)
    implementation(project(":libs:avro-schemas"))
}

springBoot {
    mainClass.set("ru.ycan.analytics.service.Application")
}

tasks.bootJar {
    archiveFileName.set("app.jar")
}

configurations.all {
    exclude(group = "org.apache.logging.log4j", module = "log4j-slf4j2-impl")
    exclude(group = "org.apache.logging.log4j", module = "log4j-to-slf4j")
}