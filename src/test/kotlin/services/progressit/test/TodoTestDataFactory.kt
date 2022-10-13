package services.progressit.test

import services.progressit.domain.model.Todo
import services.progressit.rest.dto.TodoDto

fun createGetAll() = listOf(
    createTodo1(),
    createTodo2()
)

fun createTodo1() = createTodo1WithAttributesKnownInRequest().apply {
    id = TODO_1_ID
}

fun createTodo1WithAttributesKnownInRequest() = Todo().apply {
    title = TODO_1_TITLE
    description = TODO_1_DESCRIPTION
    deadline = TODO_1_DEADLINE
}

fun createTodo2() = Todo().apply {
    id = TODO_2_ID
    title = TODO_2_TITLE
    description = TODO_2_DESCRIPTION
    deadline = TODO_2_DEADLINE
}

fun createTodo1RequestDto() = TodoDto(
    id = null,
    title = TODO_1_TITLE,
    description = TODO_1_DESCRIPTION,
    deadline = TODO_1_DEADLINE
)
