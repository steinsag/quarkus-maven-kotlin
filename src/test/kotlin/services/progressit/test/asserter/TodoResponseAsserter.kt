package services.progressit.test.asserter

import io.restassured.http.ContentType.JSON
import io.restassured.response.ValidatableResponse
import org.hamcrest.core.Is.`is`
import services.progressit.test.TODO_1_DEADLINE_STRING
import services.progressit.test.TODO_1_DESCRIPTION
import services.progressit.test.TODO_1_TITLE
import services.progressit.test.TODO_2_DEADLINE_STRING
import services.progressit.test.TODO_2_DESCRIPTION
import services.progressit.test.TODO_2_TITLE

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
}
