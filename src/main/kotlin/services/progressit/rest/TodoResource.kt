package services.progressit.rest

import jakarta.inject.Inject
import jakarta.ws.rs.NotFoundException
import jakarta.ws.rs.Path
import jakarta.ws.rs.core.Response
import services.progressit.domain.TodoService
import services.progressit.rest.dto.TodoDto
import java.net.URI

private const val TODO_BASE_PATH = "todos"

@Path("/$TODO_BASE_PATH")
class TodoResource : TodoResourceDocumentation() {

    @Inject
    lateinit var todoService: TodoService

    override fun get(id: String): TodoDto = when (val todo = todoService.get(id)) {
        null -> throw NotFoundException("Todo with ID $id not found")
        else -> TodoDto.fromDomain(todo)
    }

    override fun getAll() = todoService.getAll().map { TodoDto.fromDomain(it) }

    override fun createTodo(todoDto: TodoDto): Response {
        val todo = TodoDto.toDomain(todoDto)

        val newTodo = todoService.create(todo)

        return Response.created(URI.create("$TODO_BASE_PATH/${newTodo.id!!}")).build()
    }
}
