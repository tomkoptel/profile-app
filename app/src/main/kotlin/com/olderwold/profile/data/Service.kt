package com.olderwold.profile.data

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.http.ContentType.Application
import io.ktor.http.takeFrom
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import okhttp3.logging.HttpLoggingInterceptor

internal class Service(private val httpClient: HttpClient) {
    companion object {
        operator fun <T : HttpClientEngineConfig> invoke(
            engineFactory: HttpClientEngineFactory<T>,
            block: HttpClientConfig<T>.() -> Unit = {}
        ): Service {
            val client = HttpClient(engineFactory) {
                install(JsonFeature) {
                    serializer = KotlinxSerializer(Json(JsonConfiguration.Stable))
                }
                apply(block)
            }
            return Service(client)
        }
    }

    suspend fun dashboard(): DashboardDTO = httpClient.get {
        accept(Application.Json)
        url {
            takeFrom("https://api.jsonbin.io/b/5e7f44f858b91810000b324a/4")
        }
    }
}

internal fun okHttpService() : Service {
    return Service(OkHttp) {
        engine {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
    }
}
