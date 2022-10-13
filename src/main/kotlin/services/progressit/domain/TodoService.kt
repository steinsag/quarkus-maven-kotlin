package services.progressit.domain

import services.progressit.domain.model.Todo
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class TodoService {

    fun getAll() = emptyList<Todo>()
}
