package services.progressit.rest

import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.junit.mockito.InjectMock
import io.restassured.RestAssured.given
import org.junit.jupiter.api.Test
import org.mockito.kotlin.whenever
import services.progressit.domain.TodoService
import services.progressit.test.asserter.TodoResponseAsserter
import services.progressit.test.createGetAll
import javax.enterprise.inject.Default

@QuarkusTest
class TodoResourceTest {

    @InjectMock
    @field: Default
    lateinit var todoService: TodoService

    @Test
    fun `getAll - several todos present - returns list of todos`() {
        whenever(todoService.getAll()).thenReturn(createGetAll())

        val actualResponse = requestAllTodos()

        TodoResponseAsserter.assertGetAllResponse(actualResponse)
    }

    @Test
    fun `getAll - no todos - returns empty list`() {
        whenever(todoService.getAll()).thenReturn(emptyList())

        val actualResponse = requestAllTodos()

        TodoResponseAsserter.assertEmptyGetAllResponse(actualResponse)
    }

    private fun requestAllTodos() = given()
        .`when`()
        .get("/todos")
        .then()
}
