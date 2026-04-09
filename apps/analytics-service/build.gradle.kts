plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    compileOnly(Deps.Other.lombok)
    annotationProcessor(Deps.Other.lombok)

    implementation(Deps.Spring.springBootStarter)
    implementation(Deps.Spring.springKafka)

    implementation(Deps.Other.hadoopClient)
    implementation(Deps.Other.sparkSql)
    implementation(Deps.Other.sparkSqlKafka)
}