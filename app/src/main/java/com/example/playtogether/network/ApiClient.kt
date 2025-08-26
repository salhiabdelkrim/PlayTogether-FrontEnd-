package com.example.playtogether.network

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object ApiClient {
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
        install(HttpTimeout) {
            connectTimeoutMillis = 15_000  // 15s
            requestTimeoutMillis = 30_000 // 30s
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL // Log TOUT (requêtes, headers, body, réponses…)
        }
    }
}
