package services.progressit.domain

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import services.progressit.domain.model.Todo
import services.progressit.domain.persistence.TodoRepository

@Transactional
@ApplicationScoped
class TodoService {
    @Inject
    protected lateinit var todoRepository: TodoRepository

    fun get(id: String): Todo? = todoRepository.findById(id)

    fun getAll() = todoRepository.findAll().list()

    fun create(todo: Todo): Todo {
        todoRepository.persist(todo)

        return todo
    }
}
