plugins {
    id("org.springframework.boot") version Versions.springBoot
    id("io.spring.dependency-management") version "1.1.7"
}

dependencies {
    compileOnly(Deps.Other.lombok)
    annotationProcessor(Deps.Other.lombok)

    implementation(Deps.Spring.springBootStarter)
    implementation(Deps.Spring.springKafka)

    implementation(Deps.Other.jacksonDatabind)
    implementation(Deps.Other.jacksonDatatypeJsr310)
    implementation(Deps.Other.kafkaAvroSerializer)
    implementation(project(":libs:avro-schemas"))
}

springBoot {
    mainClass.set("ru.ycan.shop.producer.Application")
}