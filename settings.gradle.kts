rootProject.name = "final-project-kafka"

include(
    ":apps:analytics-service",
    ":apps:blacklist-service",
    ":apps:client-service",
    ":apps:shop-service"
)

include(":libs:avro-schemas")