package services.progressit.test.asserter

import io.restassured.response.ValidatableResponse
import jakarta.ws.rs.core.HttpHeaders.CONTENT_TYPE
import jakarta.ws.rs.core.MediaType.APPLICATION_JSON
import org.hamcrest.Matchers.endsWith
import org.hamcrest.core.Is.`is`
import services.progressit.rest.dto.TodoDto
import services.progressit.test.data.TODO_1_DEADLINE
import services.progressit.test.data.TODO_1_DESCRIPTION
import services.progressit.test.data.TODO_1_ID
import services.progressit.test.data.TODO_1_TITLE
import services.progressit.test.data.TODO_BASE_PATH

private const val HEADER_LOCATION = "LOCATION"
private const val UUID_LENGTH = 36

object TodoResponseAsserter {

    fun assertGetAllResponse(
        actualResponse: ValidatableResponse,
        expectedTodos: List<TodoDto>
    ) {
        actualResponse
            .statusCode(200)
            .header(CONTENT_TYPE, APPLICATION_JSON)
            .body("$.size()", `is`(expectedTodos.size))

        expectedTodos.forEachIndexed { index, expectedTodo ->
            actualResponse
                .body("[$index].id", `is`(expectedTodo.id))
                .body("[$index].title", `is`(expectedTodo.title))
                .body("[$index].description", `is`(expectedTodo.description))

            val actualDeadline = actualResponse.extract().path<String>("[$index].deadline")
            OffsetDateTimeAsserter.assertThat(actualDeadline).isEqualTo(expectedTodo.deadline)
        }
    }

    fun assertEmptyGetAllResponse(actualResponse: ValidatableResponse) {
        actualResponse
            .statusCode(200)
            .header(CONTENT_TYPE, APPLICATION_JSON)
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

    fun assertGetResponse(actualResponse: ValidatableResponse, expectedTodoId: String = TODO_1_ID) {
        actualResponse
            .statusCode(200)
            .header(CONTENT_TYPE, APPLICATION_JSON)
            .body("id", `is`(expectedTodoId))
            .body("title", `is`(TODO_1_TITLE))
            .body("description", `is`(TODO_1_DESCRIPTION))

        val actualDeadline = actualResponse.extract().path<String>("deadline")
        OffsetDateTimeAsserter.assertThat(actualDeadline).isEqualTo(TODO_1_DEADLINE)
    }

    fun assertNotFoundResponse(actualResponse: ValidatableResponse) {
        actualResponse
            .statusCode(404)
    }
}
