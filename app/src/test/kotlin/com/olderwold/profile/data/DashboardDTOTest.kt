package com.olderwold.profile.data

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.junit.Test

class DashboardDTOTest {
    private val json = Json(JsonConfiguration.Stable)

    @Test
    fun `deserialize with partially delivered info`() {
        val rawJson = """
        {
            "profile": {
                "firstName": "Bilbo",
                "lastName": "Baggins",
                "avatarUrl": "http://localhost/avatar",
                "profileUrl": "http://localhost/profile"
            },
            "frameworks": [
                {
                    "startDate": 1325372400000,
                    "name": "Android"
                },
                {
                    "startDate": 1325372400000,
                    "endDate": 1388530800000,
                    "name": "RoR"
                }
            ]
        }
        """.trimIndent()

        val obj = deserialize(rawJson)
        val android = obj.frameworks?.first()
        val ror = obj.frameworks?.get(1)

        assert(android?.startDate == 1325372400000)
        assert(android?.name == "Android")

        assert(ror?.startDate == 1325372400000)
        assert(ror?.endDate == 1388530800000)
        assert(ror?.name == "RoR")
    }

    @Test
    fun `deserialize without name`() {
        val rawJson = """
            {
                "frameworks":[
                    {"startDate": 1585388826446, "endDate": 1585388826446}
                ]
            }
        """.trimIndent()

        val obj = deserialize(rawJson)
        val framework = obj.frameworks?.first()

        assert(framework?.startDate == 1585388826446)
        assert(framework?.endDate == 1585388826446)
    }

    @Test
    fun `deserialize without frameworks`() {
        val rawJson = """
            {
                "frameworks": null
            }
        """.trimIndent()

        val obj = deserialize(rawJson)

        assert(obj.frameworks == null)
    }

    @Test
    fun `deserialize empty json`() {
        val rawJson = """
            {}
        """.trimIndent()

        val obj = deserialize(rawJson)

        assert(obj.frameworks == null)
    }

    private fun deserialize(rawJson: String): DashboardDTO {
        return json.parse(DashboardDTO.serializer(), rawJson)
    }
}
