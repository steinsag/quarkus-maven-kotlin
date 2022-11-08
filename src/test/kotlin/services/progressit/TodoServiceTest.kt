package services.progressit

import io.quarkus.test.junit.QuarkusTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import services.progressit.domain.TodoService
import services.progressit.domain.model.Todo
import services.progressit.test.asserter.DatabaseAsserter
import services.progressit.test.asserter.UuidAsserter
import services.progressit.test.createTodo1WithAttributesKnownInRequest
import javax.inject.Inject

@QuarkusTest
class TodoServiceTest {

    @Inject
    lateinit var todoService: TodoService

    @Inject
    lateinit var databaseAsserter: DatabaseAsserter

    @Test
    fun `createTodo - valid todo - todo is persisted`() {
        val givenTodo = createTodo1WithAttributesKnownInRequest()

        val actualTodo = todoService.create(givenTodo)

        assertServiceResponse(actualTodo, givenTodo)
        databaseAsserter.assertNumberOfTodos(1)
        databaseAsserter.assertTodoEntity(actualTodo.id!!, createTodo1WithAttributesKnownInRequest())
    }

    private fun assertServiceResponse(actualTodo: Todo, givenTodo: Todo) {
        assertThat(actualTodo.id).isNotNull
        UuidAsserter.assertThat(actualTodo.id).isUuid()
        assertThat(actualTodo).usingRecursiveComparison().ignoringFields("id").isEqualTo(givenTodo)
    }
}
