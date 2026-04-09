rootProject.name = "final-project-kafka"

include(
    ":apps:analytics-service",
    ":apps:blacklist-service",
    ":apps:client-service",
    ":apps:shop-producer"
)

include(":libs:avro-schemas")