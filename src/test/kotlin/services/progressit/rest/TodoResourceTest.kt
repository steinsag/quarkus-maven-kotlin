package services.progressit.rest

import io.quarkus.test.InjectMock
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import jakarta.enterprise.inject.Default
import jakarta.ws.rs.core.HttpHeaders.ACCEPT
import jakarta.ws.rs.core.MediaType.APPLICATION_JSON
import jakarta.ws.rs.core.MediaType.APPLICATION_XML
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.kotlin.KArgumentCaptor
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever
import services.progressit.domain.TodoService
import services.progressit.domain.model.Todo
import services.progressit.rest.dto.TodoDto
import services.progressit.test.asserter.TodoResponseAsserter
import services.progressit.test.data.TODO_1_ID
import services.progressit.test.data.TODO_2_ID
import services.progressit.test.data.TODO_BASE_PATH
import services.progressit.test.data.createTodo1
import services.progressit.test.data.createTodo1RequestDto
import services.progressit.test.data.createTodo1ResponseDto
import services.progressit.test.data.createTodo1WithAttributesKnownInRequest
import services.progressit.test.data.createTodo2
import services.progressit.test.data.createTodo2ResponseDto

@QuarkusTest
class TodoResourceTest : BaseResourceTest() {

    @InjectMock
    @field: Default
    lateinit var todoService: TodoService

    @Nested
    inner class Get {

        @Test
        fun `get - todo with given id exists - returns todo`() {
            whenever(todoService.get(TODO_1_ID)).thenReturn(createTodo1())

            val actualResponse = requestTodoById(TODO_1_ID)

            verify(todoService).get(TODO_1_ID)
            TodoResponseAsserter.assertGetResponse(actualResponse)
        }

        @Test
        fun `get - todo with given id does not exist - not found 404`() {
            whenever(todoService.get(TODO_1_ID)).thenReturn(createTodo1())
            whenever(todoService.get(TODO_2_ID)).thenReturn(null)

            val actualResponse = requestTodoById(TODO_2_ID)

            verify(todoService).get(TODO_2_ID)
            TodoResponseAsserter.assertNotFoundResponse(actualResponse)
        }

        @Test
        fun `get - request XML response - not acceptable 406`() {
            given()
                .header(ACCEPT, APPLICATION_XML)
                .`when`()
                .get("$TODO_BASE_PATH/$TODO_1_ID")
                .then()
                .statusCode(406)

            verifyNoInteractions(todoService)
        }

        @Test
        fun `get - exception thrown in service - internal server error 500`() {
            whenever(todoService.get(any())).thenThrow(RuntimeException("test exception"))

            val actualResponse = requestTodoById(TODO_1_ID)

            actualResponse.statusCode(500)
        }

        private fun requestTodoById(id: String) = given()
            .header(ACCEPT, APPLICATION_JSON)
            .`when`()
            .get("/$TODO_BASE_PATH/$id")
            .then()
    }

    @Nested
    inner class GetAll {

        @Test
        fun `getAll - several todos present - returns list of todos`() {
            val givenTodos = listOf(createTodo1(), createTodo2())
            whenever(todoService.getAll()).thenReturn(givenTodos)

            val actualResponse = requestAllTodos()

            verify(todoService).getAll()
            TodoResponseAsserter.assertGetAllResponse(
                actualResponse,
                listOf(createTodo1ResponseDto(), createTodo2ResponseDto())
            )
        }

        @Test
        fun `getAll - no todos - returns empty list`() {
            whenever(todoService.getAll()).thenReturn(emptyList())

            val actualResponse = requestAllTodos()

            verify(todoService).getAll()
            TodoResponseAsserter.assertEmptyGetAllResponse(actualResponse)
        }

        @Test
        fun `getAll - request XML response - not acceptable  406`() {
            given()
                .header(ACCEPT, APPLICATION_XML)
                .`when`()
                .get(TODO_BASE_PATH)
                .then()
                .statusCode(406)

            verifyNoInteractions(todoService)
        }

        @Test
        fun `getAll - exception thrown in service - internal server error 500`() {
            whenever(todoService.getAll()).thenThrow(RuntimeException("test exception"))

            val actualResponse = requestAllTodos()

            actualResponse.statusCode(500)
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

        @Test
        fun `post - invalid JSON - bad request 400`() {
            given()
                .contentType(APPLICATION_JSON)
                .body("invalid json")
                .post("/$TODO_BASE_PATH")
                .then().statusCode(400)

            verifyNoInteractions(todoService)
        }

        @Test
        fun `post - send XML - unsupported media type 415`() {
            given()
                .contentType(APPLICATION_XML)
                .body("<todo><title>title</title></todo>")
                .post("/$TODO_BASE_PATH")
                .then()
                .statusCode(415)

            verifyNoInteractions(todoService)
        }

        @Test
        fun `post - exception thrown in service - internal server error 500`() {
            whenever(todoService.create(any())).thenThrow(RuntimeException("test exception"))

            val actualResponse = postTodo(createTodo1RequestDto())

            actualResponse.statusCode(500)
        }

        private fun postTodo(givenTodoDto: TodoDto) = given()
            .contentType(APPLICATION_JSON)
            .body(givenTodoDto)
            .post("/$TODO_BASE_PATH")
            .then()
    }
}
