package services.progressit.test.asserter

import io.restassured.http.ContentType.JSON
import io.restassured.response.ValidatableResponse
import org.hamcrest.Matchers.endsWith
import org.hamcrest.core.Is.`is`
import services.progressit.test.TODO_1_DEADLINE_STRING
import services.progressit.test.TODO_1_DESCRIPTION
import services.progressit.test.TODO_1_TITLE
import services.progressit.test.TODO_2_DEADLINE_STRING
import services.progressit.test.TODO_2_DESCRIPTION
import services.progressit.test.TODO_2_TITLE
import services.progressit.test.TODO_BASE_PATH

private const val HEADER_LOCATION = "LOCATION"
private const val UUID_LENGTH = 36

object TodoResponseAsserter {

    fun assertGetAllResponse(actualResponse: ValidatableResponse) {
        actualResponse
            .statusCode(200)
            .contentType(JSON)
            .body("$.size()", `is`(2))
            .body("[0].title", `is`(TODO_1_TITLE))
            .body("[0].description", `is`(TODO_1_DESCRIPTION))
            .body("[0].deadline", `is`(TODO_1_DEADLINE_STRING))
            .body("[1].title", `is`(TODO_2_TITLE))
            .body("[1].description", `is`(TODO_2_DESCRIPTION))
            .body("[1].deadline", `is`(TODO_2_DEADLINE_STRING))
    }

    fun assertEmptyGetAllResponse(actualResponse: ValidatableResponse) {
        actualResponse
            .statusCode(200)
            .contentType(JSON)
            .body("$.size()", `is`(0))
    }

    fun assertCreateResponse(actualResponse: ValidatableResponse, expectedTodoId: String) {
        actualResponse
            .statusCode(201)
            .header(HEADER_LOCATION, endsWith("/$TODO_BASE_PATH/$expectedTodoId"))
    }

    fun assertCreateResponse(actualResponse: ValidatableResponse) {
        val actualTodoId = extractTodoId(actualResponse)
        UuidAsserter.assertThat(actualTodoId).isUuid()

        assertCreateResponse(actualResponse, actualTodoId)
    }

    fun extractTodoId(actualResponse: ValidatableResponse) =
        actualResponse.extract().header(HEADER_LOCATION).takeLast(UUID_LENGTH)
}
