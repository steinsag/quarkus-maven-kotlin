package services.progressit.rest.dto

import services.progressit.domain.model.Todo
import java.time.OffsetDateTime

data class TodoDto(
    val id: String?,
    val title: String,
    val description: String,
    val deadline: OffsetDateTime
) {
    companion object {
        fun toDomain(todoDto: TodoDto) =
            Todo().apply {
                title = todoDto.title
                description = todoDto.description
                deadline = todoDto.deadline
            }

        fun fromDomain(todo: Todo) =
            TodoDto(
                id = todo.id,
                title = todo.title,
                description = todo.description,
                deadline = todo.deadline
            )
    }
}
