package services.progressit.componenttest

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import jakarta.ws.rs.core.MediaType.APPLICATION_JSON
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import services.progressit.rest.dto.TodoDto
import services.progressit.test.CleanDatabaseAfterEach
import services.progressit.test.asserter.DatabaseAsserter
import services.progressit.test.asserter.TodoResponseAsserter
import services.progressit.test.data.TODO_BASE_PATH
import services.progressit.test.data.createTodo1RequestDto
import services.progressit.test.data.createTodo1WithAttributesKnownInRequest
import services.progressit.test.data.createTodo2RequestDto

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

    @Test
    fun `get - todo with given id exists - returns todo`() {
        val givenTodoId = createTodo().id!!

        val actualResponse = getTodoById(givenTodoId)

        TodoResponseAsserter.assertGetResponse(actualResponse, givenTodoId)
    }

    @Test
    fun `getAll - several todos present - returns list of todos`() {
        val givenTodo1 = createTodo(createTodo1RequestDto())
        val givenTodo2 = createTodo(createTodo2RequestDto())
        assertThat(givenTodo1.id).isNotEqualTo(givenTodo2.id)

        val actualResponse = getAllTodos()

        TodoResponseAsserter.assertGetAllResponse(actualResponse, listOf(givenTodo1, givenTodo2))
    }

    private fun getTodoById(givenTodoId: String) = given()
        .get("/$TODO_BASE_PATH/$givenTodoId")
        .then()

    private fun getAllTodos() = given()
        .get("/$TODO_BASE_PATH")
        .then()

    private fun createTodo(todoDto: TodoDto = createTodo1RequestDto()): TodoDto {
        val response = postTodo(todoDto)

        val todoId = TodoResponseAsserter.extractTodoId(response)
        assertThat(todoId).isNotNull

        return todoDto.copy(id = todoId)
    }

    private fun postTodo(givenTodoDto: TodoDto) = given()
        .contentType(APPLICATION_JSON)
        .body(givenTodoDto)
        .post("/$TODO_BASE_PATH")
        .then()
}
