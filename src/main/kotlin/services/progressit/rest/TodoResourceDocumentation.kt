package services.progressit.rest

import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.jboss.resteasy.annotations.jaxrs.PathParam
import services.progressit.rest.dto.TodoDto
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType.APPLICATION_JSON
import javax.ws.rs.core.Response

@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
abstract class TodoResourceDocumentation {

    @Operation(summary = "Get todo by ID")
    @GET
    @Path("/{id}")
    abstract fun get(@PathParam id: String): TodoDto

    @GET
    abstract fun getAll(): List<TodoDto>

    @Operation(summary = "Create a new todo")
    @APIResponse(responseCode = "201", description = "Todo created")
    @POST
    abstract fun createTodo(todoDto: TodoDto): Response
}
