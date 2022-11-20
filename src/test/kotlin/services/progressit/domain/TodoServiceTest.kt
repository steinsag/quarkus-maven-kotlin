package services.progressit.domain

import io.quarkus.test.junit.QuarkusTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import services.progressit.domain.model.Todo
import services.progressit.test.CleanDatabaseAfterEach
import services.progressit.test.asserter.DatabaseAsserter
import services.progressit.test.asserter.UuidAsserter
import services.progressit.test.data.TODO_1_ID
import services.progressit.test.data.createTodo1WithAttributesKnownInRequest
import javax.inject.Inject
import javax.transaction.Transactional

@QuarkusTest
@Transactional
class TodoServiceTest : CleanDatabaseAfterEach() {

    @Inject
    lateinit var todoService: TodoService

    @Inject
    lateinit var databaseAsserter: DatabaseAsserter

    @Test
    fun `get - todo with given id exists - returns todo`() {
        val givenTodo = createTodo1WithAttributesKnownInRequest()
        todoRepository.persist(givenTodo)
        val givenTodoId = givenTodo.id!!

        val actualTodo = todoService.get(givenTodoId)

        assertThat(actualTodo).isNotNull
        assertServiceResponse(actualTodo!!, givenTodo)
    }

    @Test
    fun `get - todo doesn't exist - returns null`() {
        val actualTodo = todoService.get(TODO_1_ID)

        assertThat(actualTodo).isNull()
    }

    @Test
    fun `getAllTodos - no todos stored - empty list returned`() {
        val actualTodos = todoService.getAll()

        assertThat(actualTodos).isEmpty()
    }

    @Test
    fun `getAllTodos - one todo present - list with todo returned`() {
        todoRepository.persist(createTodo1WithAttributesKnownInRequest())

        val actualTodos = todoService.getAll()

        assertThat(actualTodos).hasSize(1)
    }

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
