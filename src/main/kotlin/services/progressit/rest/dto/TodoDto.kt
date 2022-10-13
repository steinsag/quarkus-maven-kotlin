package services.progressit.rest.dto

import services.progressit.domain.model.Todo
import java.time.OffsetDateTime

data class TodoDto(
    val title: String,
    val description: String,
    val deadline: OffsetDateTime
) {
    companion object {
        fun fromDomain(todo: Todo) = TodoDto(
            title = todo.title,
            description = todo.description,
            deadline = todo.deadline
        )
    }
}
