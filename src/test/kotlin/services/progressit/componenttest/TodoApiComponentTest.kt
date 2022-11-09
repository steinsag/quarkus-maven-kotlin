package services.progressit.componenttest

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.junit.jupiter.api.Test
import services.progressit.rest.dto.TodoDto
import services.progressit.test.CleanDatabaseAfterEach
import services.progressit.test.asserter.DatabaseAsserter
import services.progressit.test.asserter.TodoResponseAsserter
import services.progressit.test.data.TODO_BASE_PATH
import services.progressit.test.data.createTodo1RequestDto
import services.progressit.test.data.createTodo1WithAttributesKnownInRequest
import javax.inject.Inject
import javax.transaction.Transactional
import javax.ws.rs.core.MediaType.APPLICATION_JSON

@QuarkusTest
@Transactional
class TodoApiComponentTest : CleanDatabaseAfterEach() {

    @Inject
    lateinit var databaseAsserter: DatabaseAsserter

    @Test
    fun `createTodo - valid todo - todo created`() {
        val givenTodoDto = createTodo1RequestDto()

        val actualResponse = postTodo(givenTodoDto)

        TodoResponseAsserter.assertCreateResponse(actualResponse)
        databaseAsserter.assertTodoEntity(
            TodoResponseAsserter.extractTodoId(actualResponse),
            createTodo1WithAttributesKnownInRequest()
        )
    }

    private fun postTodo(givenTodoDto: TodoDto) = given()
        .contentType(APPLICATION_JSON)
        .body(givenTodoDto)
        .post("/$TODO_BASE_PATH")
        .then()
}
