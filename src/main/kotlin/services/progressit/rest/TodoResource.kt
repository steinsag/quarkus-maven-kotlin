package services.progressit.rest

import services.progressit.domain.TodoService
import services.progressit.rest.dto.TodoDto
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType.APPLICATION_JSON

@Path("/todos")
class TodoResource {

    @Inject
    lateinit var todoService: TodoService

    @GET
    @Produces(APPLICATION_JSON)
    fun getAll() = todoService.getAll().map { TodoDto.fromDomain(it) }
}
