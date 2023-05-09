package services.progressit.domain.persistence

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped
import services.progressit.domain.model.Todo

@ApplicationScoped
class TodoRepository : PanacheRepositoryBase<Todo, String>
