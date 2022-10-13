package services.progressit

import io.quarkus.test.junit.QuarkusTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import services.progressit.domain.TodoService
import services.progressit.domain.persistence.TodoRepository
import services.progressit.test.TODO_1_DEADLINE
import services.progressit.test.TODO_1_DESCRIPTION
import services.progressit.test.TODO_1_TITLE
import services.progressit.test.createTodo1WithAttributesKnownInRequest
import javax.inject.Inject

@QuarkusTest
class TodoServiceTest {

    @Inject
    lateinit var todoService: TodoService

    @Inject
    lateinit var todoRepository: TodoRepository

    @Test
    fun `createTodo - valid todo - todo is persisted`() {
        val givenTodo = createTodo1WithAttributesKnownInRequest()

        val actualTodo = todoService.create(givenTodo)

        assertThat(actualTodo.id).isNotNull
        assertThat(actualTodo).usingRecursiveComparison().ignoringFields("id").isEqualTo(givenTodo)

        assertThat(todoRepository.findAll().count()).isOne
        val actualEntity = todoRepository.findById(actualTodo.id!!)
        assertThat(actualEntity).isNotNull
        assertThat(actualEntity!!.id).isEqualTo(actualTodo.id)
        assertThat(actualEntity.title).isEqualTo(TODO_1_TITLE)
        assertThat(actualEntity.description).isEqualTo(TODO_1_DESCRIPTION)
        assertThat(actualEntity.deadline).isEqualTo(TODO_1_DEADLINE)
    }
}
