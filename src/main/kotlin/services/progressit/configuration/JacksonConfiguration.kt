package services.progressit.configuration

import com.fasterxml.jackson.databind.DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE
import com.fasterxml.jackson.databind.ObjectMapper
import io.quarkus.jackson.ObjectMapperCustomizer
import jakarta.inject.Singleton

@Singleton
class JacksonConfiguration : ObjectMapperCustomizer {

    override fun customize(objectMapper: ObjectMapper?) {
        objectMapper?.disable(ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
    }
}
