package services.progressit.rest

import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.junit.mockito.InjectMock
import io.restassured.RestAssured.given
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.kotlin.KArgumentCaptor
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import services.progressit.domain.TodoService
import services.progressit.domain.model.Todo
import services.progressit.rest.dto.TodoDto
import services.progressit.test.TODO_1_ID
import services.progressit.test.TODO_BASE_PATH
import services.progressit.test.asserter.TodoResponseAsserter
import services.progressit.test.createGetAll
import services.progressit.test.createTodo1
import services.progressit.test.createTodo1RequestDto
import services.progressit.test.createTodo1WithAttributesKnownInRequest
import javax.enterprise.inject.Default
import javax.ws.rs.core.MediaType.APPLICATION_JSON

@QuarkusTest
class TodoResourceTest : BaseResourceTest() {

    @InjectMock
    @field: Default
    lateinit var todoService: TodoService

    @Nested
    inner class Get {

        @Test
        fun `getAll - several todos present - returns list of todos`() {
            whenever(todoService.getAll()).thenReturn(createGetAll())

            val actualResponse = requestAllTodos()

            verify(todoService).getAll()
            TodoResponseAsserter.assertGetAllResponse(actualResponse)
        }

        @Test
        fun `getAll - no todos - returns empty list`() {
            whenever(todoService.getAll()).thenReturn(emptyList())

            val actualResponse = requestAllTodos()

            verify(todoService).getAll()
            TodoResponseAsserter.assertEmptyGetAllResponse(actualResponse)
        }

        private fun requestAllTodos() = given()
            .`when`()
            .get("/$TODO_BASE_PATH")
            .then()
    }

    @Nested
    inner class Create {

        private lateinit var todoCaptor: KArgumentCaptor<Todo>

        @BeforeEach
        fun reset() {
            todoCaptor = argumentCaptor()
        }

        @Test
        fun `post - valid todo - created`() {
            whenever(todoService.create(any())).thenReturn(createTodo1())
            val givenTodoDto = createTodo1RequestDto()

            val actualResponse = postTodo(givenTodoDto)

            verify(todoService).create(todoCaptor.capture())
            assertThat(todoCaptor.firstValue)
                .usingRecursiveComparison()
                .isEqualTo(createTodo1WithAttributesKnownInRequest())
            TodoResponseAsserter.assertCreateResponse(actualResponse, TODO_1_ID)
        }

        private fun postTodo(givenTodoDto: TodoDto) = given()
            .contentType(APPLICATION_JSON)
            .body(givenTodoDto)
            .post("/$TODO_BASE_PATH")
            .then()
    }
}
