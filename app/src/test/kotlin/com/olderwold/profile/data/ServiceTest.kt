package com.olderwold.profile.data

import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class ServiceTest {
    @Test
    fun testDashboard() = runBlocking {
        val api = Service(MockEngine) {
            engine {
                requestHandlers.add {
                    respond(
                        content = """{"frameworks": null }""",
                        status = HttpStatusCode.OK,
                        headers = headersOf("Content-Type", ContentType.Application.Json.toString())
                    )
                }
            }
        }
        assert(api.dashboard().frameworks == null)
    }
}
