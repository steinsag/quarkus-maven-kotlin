package services.progressit.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit.MILLIS

@QuarkusTest
class JacksonConfigurationTest {
    private val timestampWithTimezoneAsString = "2022-10-01T19:51:37.739+02:00"
    private val timestampWithTimezone = OffsetDateTime.parse(timestampWithTimezoneAsString).truncatedTo(MILLIS)

    @Inject
    lateinit var objectMapper: ObjectMapper

    @Test
    fun `deserialize JSON - timestamp given - not converted to UTC`() {
        val givenJson = "{\"timestamp\": \"${timestampWithTimezoneAsString}\"}"

        val actualDto = objectMapper.readValue(givenJson, TestDto::class.java)

        assertThat(actualDto.timestamp.toString()).isEqualTo(timestampWithTimezoneAsString)
        assertThat(actualDto.timestamp).isEqualTo(timestampWithTimezone)
    }

    @Test
    fun `serialize JSON - timestamp given - contains timezone`() {
        val givenDto = TestDto(timestamp = timestampWithTimezone)

        val actualJson = objectMapper.writeValueAsString(givenDto)

        assertThat(actualJson).contains(timestampWithTimezoneAsString)
    }

    data class TestDto(
        val timestamp: OffsetDateTime? = null
    )
}
