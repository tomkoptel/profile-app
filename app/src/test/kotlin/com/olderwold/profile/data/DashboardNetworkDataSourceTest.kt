package com.olderwold.profile.data

import com.olderwold.profile.domain.Dashboard
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class DashboardNetworkDataSourceTest {
    @Test
    fun `verify mapping performed after service call`() {
        val dashboardDto = mockk<DashboardDTO>()
        val service = mockk<Service> {
            coEvery { dashboard() } returns dashboardDto
        }
        val dashboardDomain = mockk<Dashboard>()
        val dashboardMapper = mockk<DashboardMapper> {
            every { toDomain(any()) } returns dashboardDomain
        }

        val dataSource = DashboardNetworkDataSource(service, dashboardMapper)

        runBlocking {
            assert(dataSource.dashboard() == dashboardDomain)
        }
    }
}
