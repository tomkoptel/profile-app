package com.olderwold.profile.data

import com.olderwold.profile.domain.Framework
import com.olderwold.profile.domain.Profile
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class DashboardMapperTest {
    private val anyProfile = mockk<Profile>()
    private val anyFramework = mockk<Framework>()
    private val profileMapper = mockk<ProfileMapper>() {
        every { toDomain(any()) } returns anyProfile
    }
    private val frameworkMapper = mockk<FrameworkMapper> {
        every { toDomain(any()) } returns anyFramework
    }
    private val dashboardMapper = DashboardMapper(frameworkMapper, profileMapper)

    @Test
    fun `maps null framework list to empty collection`() {
        val dashboard = dashboardMapper.toDomain(DashboardDTO(null))

        assert(dashboard.frameworks.isEmpty())
    }

    @Test
    fun `map filters out null framework instances`() {
        every { frameworkMapper.toDomain(any()) } returns null

        val dashboard = dashboardMapper.toDomain(DashboardDTO(frameworks = listOf(null, null)))

        assert(dashboard.frameworks.isEmpty())
    }

    @Test
    fun `map delegates mapping of frameworks`() {
        val dashboard = dashboardMapper.toDomain(DashboardDTO(frameworks = listOf(mockk())))

        assert(dashboard.frameworks.contains(anyFramework))
    }

    @Test
    fun `map delegates mapping of profileDTO`() {
        val profileDTO = mockk<ProfileDTO>()
        val dashboard = dashboardMapper.toDomain(DashboardDTO(profile = profileDTO))

        verify { profileMapper.toDomain(profileDTO) }
        assert(dashboard.profile == anyProfile)
    }
}
