package services.progressit.test.asserter

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.assertj.core.api.Assertions.assertThat
import services.progressit.domain.model.Todo
import services.progressit.domain.persistence.TodoRepository
import java.time.OffsetDateTime

private const val HIBERNATE_ATTRIBUTES_IGNORE_PATTERN = "\\\$\\\$_hibernate_.*"

@ApplicationScoped
class DatabaseAsserter {

    @Inject
    private lateinit var todoRepository: TodoRepository

    fun assertNumberOfTodos(expectedNumberOfTodos: Long) {
        assertThat(todoRepository.findAll().count()).isEqualTo(expectedNumberOfTodos)
    }

    fun assertTodoEntity(todoId: String, expectedTodo: Todo) {
        val actualTodo = todoRepository.findById(todoId)

        assertThat(actualTodo).isNotNull
        assertThat(actualTodo!!.id).isEqualTo(todoId)
        assertThat(actualTodo)
            .usingComparatorForType(OffsetDateTimeComparator(), OffsetDateTime::class.java)
            .usingRecursiveComparison()
            .ignoringFieldsMatchingRegexes(HIBERNATE_ATTRIBUTES_IGNORE_PATTERN)
            .ignoringFields("id")
            .isEqualTo(expectedTodo)
    }
}
