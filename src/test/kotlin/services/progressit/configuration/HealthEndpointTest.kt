package services.progressit.configuration

import io.quarkus.test.junit.QuarkusTest
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.Test

@QuarkusTest
class HealthEndpointTest {

    @Test
    fun testHealthEndpoint() {
        When {
            get("/health")
        } Then {
            statusCode(200)
            body("status", equalTo("UP"))
        }
    }
}
