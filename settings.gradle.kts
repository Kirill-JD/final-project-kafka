rootProject.name = "final-project-kafka"

include(
    ":apps:shop-producer",
    "apps:client-service",
    "apps:common",
    "apps:stream-processor",
    "apps:analytics-service",
    "apps:blacklist-service"
)