package services.progressit.domain

import services.progressit.domain.model.Todo
import services.progressit.domain.persistence.TodoRepository
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.transaction.Transactional

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
