package services.progressit.rest

import com.fasterxml.jackson.databind.ObjectMapper
import io.restassured.RestAssured
import io.restassured.config.ObjectMapperConfig
import jakarta.inject.Inject
import org.junit.jupiter.api.BeforeEach
import java.lang.reflect.Type

open class BaseResourceTest {
    @Inject
    lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun applyObjectMapperConfigurationToRestAssured() {
        RestAssured.config =
            RestAssured.config()
                .objectMapperConfig(
                    ObjectMapperConfig().jackson2ObjectMapperFactory { _: Type, _: String -> objectMapper }
                )
    }
}
