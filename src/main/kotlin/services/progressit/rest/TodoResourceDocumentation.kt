package services.progressit.rest

import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType.APPLICATION_JSON
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.jboss.resteasy.annotations.jaxrs.PathParam
import services.progressit.rest.dto.TodoDto

@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
abstract class TodoResourceDocumentation {
    @Operation(summary = "Get todo by ID")
    @GET
    @Path("/{id}")
    abstract fun get(
        @PathParam id: String
    ): TodoDto

    @GET
    abstract fun getAll(): List<TodoDto>

    @Operation(summary = "Create a new todo")
    @APIResponse(responseCode = "201", description = "Todo created")
    @POST
    abstract fun createTodo(todoDto: TodoDto): Response
}
