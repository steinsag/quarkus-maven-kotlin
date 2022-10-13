package services.progressit.rest

import services.progressit.domain.TodoService
import services.progressit.rest.dto.TodoDto
import java.net.URI
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType.APPLICATION_JSON
import javax.ws.rs.core.Response

private const val TODO_BASE_PATH = "todos"

@Path("/$TODO_BASE_PATH")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
class TodoResource {

    @Inject
    lateinit var todoService: TodoService

    @GET
    fun getAll() = todoService.getAll().map { TodoDto.fromDomain(it) }

    @POST
    fun createTodo(todoDto: TodoDto): Response {
        val todo = TodoDto.toDomain(todoDto)

        val newTodo = todoService.create(todo)

        return Response.created(URI.create("$TODO_BASE_PATH/${newTodo.id!!}")).build()
    }
}
