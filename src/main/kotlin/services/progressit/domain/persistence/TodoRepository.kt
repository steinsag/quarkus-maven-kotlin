package services.progressit.domain.persistence

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepositoryBase
import services.progressit.domain.model.Todo
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class TodoRepository : PanacheRepositoryBase<Todo, String>
