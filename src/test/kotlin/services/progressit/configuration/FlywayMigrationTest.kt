package services.progressit.configuration

import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.assertj.core.api.Assertions.assertThat
import org.flywaydb.core.Flyway
import org.junit.jupiter.api.Test

@QuarkusTest
class FlywayMigrationTest {
    @Suppress("CdiInjectionPointsInspection")
    @Inject
    lateinit var flyway: Flyway

    @Test
    fun `startApp - app fully started - Flyway migrations executed`() {
        assertThat(flyway.info().current().version.version).isEqualTo("001")
    }
}
