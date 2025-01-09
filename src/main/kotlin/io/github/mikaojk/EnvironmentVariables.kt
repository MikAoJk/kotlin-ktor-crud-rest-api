package io.github.mikaojk

data class EnvironmentVariables(
    val databaseHostUrl: String = getEnvVar("DATABASE_HOST_URL", "localhost")
)

fun getEnvVar(varName: String, defaultValue: String? = null) =
    System.getenv(varName)
        ?: defaultValue ?: throw RuntimeException("Missing required variable \"$varName\"")
