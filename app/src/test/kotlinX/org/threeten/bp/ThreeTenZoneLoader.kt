package org.threeten.bp

import org.threeten.bp.zone.TzdbZoneRulesProvider
import org.threeten.bp.zone.ZoneRulesInitializer
import org.threeten.bp.zone.ZoneRulesProvider
import java.io.File
import java.nio.file.Paths
import java.util.concurrent.atomic.AtomicBoolean

internal fun Any.loadZones() = ZoneLoader.initialize(this)

private object ZoneLoader {
    private val initialized = AtomicBoolean()

    fun initialize(any: Any) {
        if (!initialized.getAndSet(true)) {
            val classLoader = any.javaClass.classLoader!!
            val file = File(classLoader.getResource("TZDB.dat").file)

            ZoneRulesInitializer.setInitializer(object : ZoneRulesInitializer() {
                override fun initializeProviders() {
                    lateinit var provider: TzdbZoneRulesProvider
                    Paths.get(file.toURI()).toFile().inputStream().use { stream ->
                        provider = TzdbZoneRulesProvider(stream)
                    }
                    ZoneRulesProvider.registerProvider(provider)
                }
            })
        }
    }
}
