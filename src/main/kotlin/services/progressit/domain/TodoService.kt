package services.progressit.domain

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import services.progressit.domain.model.Todo
import services.progressit.domain.persistence.TodoRepository

@Transactional
@ApplicationScoped
class TodoService(
    private val todoRepository: TodoRepository
) {
    fun get(id: String): Todo? = todoRepository.findById(id)

    fun getAll() = todoRepository.findAll().list()

    fun create(todo: Todo): Todo {
        todoRepository.persist(todo)

        return todo
    }
}
