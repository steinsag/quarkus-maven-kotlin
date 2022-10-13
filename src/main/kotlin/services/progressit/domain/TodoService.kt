package services.progressit.domain

import services.progressit.domain.model.Todo
import services.progressit.domain.persistence.TodoRepository
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.transaction.Transactional

@ApplicationScoped
class TodoService {

    @Inject
    lateinit var todoRepository: TodoRepository

    fun getAll() = emptyList<Todo>()

    @Transactional
    fun create(todo: Todo): Todo {
        todoRepository.persist(todo)

        return todo
    }
}
