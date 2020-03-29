package com.olderwold.profile.data

import org.junit.Before
import org.junit.Test
import org.threeten.bp.loadZones

class FrameworkMapperTest {
    private val mapper = FrameworkMapper()

    @Before
    fun setUp() {
        loadZones()
    }

    @Test
    fun `mapper maps framework to null`() {
        val domain = mapper.toDomain(null)

        assert(domain == null)
    }

    @Test
    fun `maps name to name`() {
        val java = mapper.toDomain(FrameworkDTO(name = "Java"))
        assert(java?.name == "Java")
    }

    @Test
    fun `maps nullable name to null`() {
        val nullVal = mapper.toDomain(FrameworkDTO(name = null))
        assert(nullVal?.name == null)
    }

    @Test
    fun `maps startDate to Date`() {
        val framework = mapper.toDomain(FrameworkDTO(startDate = 1585388826446L))
        assert(framework?.startDate != null)
    }

    @Test
    fun `maps nullable startDate to null`() {
        val nullVal = mapper.toDomain(FrameworkDTO(startDate = null))
        assert(nullVal?.startDate == null)
    }

    @Test
    fun `maps non-null endDate to Date`() {
        val framework = mapper.toDomain(FrameworkDTO(endDate = 1585388826446L))
        assert(framework?.endDate != null)
    }

    @Test
    fun `maps nullable endDate to null`() {
        val nullVal = mapper.toDomain(FrameworkDTO(endDate = null))
        assert(nullVal?.endDate == null)
    }
}
