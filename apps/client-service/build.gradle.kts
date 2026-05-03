plugins {
    id("org.springframework.boot") version Versions.springBoot
    id("io.spring.dependency-management") version "1.1.7"
}

dependencies {
    compileOnly(Deps.Other.lombok)
    annotationProcessor(Deps.Other.lombok)
    annotationProcessor(Deps.Other.mapstructProcessor)

    implementation(Deps.Spring.springBootStarter)
    implementation(Deps.Spring.springBootStarterDataElasticsearch)
    implementation(Deps.Spring.springKafka)

    implementation(Deps.Other.elasticsearch)
    implementation(Deps.Other.jacksonDatabind)
    implementation(Deps.Other.jacksonDatatypeJsr310)
    implementation(Deps.Other.kafkaAvroSerializer)
    implementation(Deps.Other.mapstruct)
    implementation(Deps.Other.picocli)
    implementation(project(":libs:avro-schemas"))
}

springBoot {
    mainClass.set("ru.ycan.client.service.Application")
}