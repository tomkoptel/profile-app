package com.olderwold.profile.domain

import org.threeten.bp.Instant
import org.threeten.bp.Period
import org.threeten.bp.ZoneId
import java.util.*

data class Framework(
    val name: String?,
    val startDate: Date?,
    val endDate: Date?,
    private val period: Period?
) {
    companion object {
        operator fun invoke(
            name: String? = null,
            startDate: Date? = null,
            endDate: Date? = null
        ) = Framework(
            name = name,
            startDate = startDate,
            endDate = endDate,
            period = startDate.computePeriod(endDate)
        )

        private fun Date?.computePeriod(endDate: Date?): Period? {
            val startDate = this
            val dateRange = when {
                startDate != null && endDate != null -> {
                    startDate to endDate
                }
                startDate != null -> {
                    startDate to Date()
                }
                else -> null
            }

            return if (dateRange == null) {
                null
            } else {
                val (_startDate, _endDate) = dateRange
                val dateStart = Instant.ofEpochMilli(_startDate.time)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                val dateEnd = Instant.ofEpochMilli(_endDate.time)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                Period.between(dateStart, dateEnd)
            }
        }
    }

    val yearsSpent: Int get() = period?.years ?: 0

    val monthsSpent: Int get() = period?.months ?: 0
}
