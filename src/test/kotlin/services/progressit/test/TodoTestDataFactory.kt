package services.progressit.test

import services.progressit.domain.model.Todo
import services.progressit.rest.dto.TodoDto

fun createGetAll() = listOf(
    createTodo1(),
    createTodo2()
)

fun createTodo1() = Todo(
    title = TODO_1_TITLE,
    description = TODO_1_DESCRIPTION,
    deadline = TODO_1_DEADLINE
)

fun createTodo2() = Todo(
    title = TODO_2_TITLE,
    description = TODO_2_DESCRIPTION,
    deadline = TODO_2_DEADLINE
)

fun createTodo1Dto() = TodoDto(
    title = TODO_1_TITLE,
    description = TODO_1_DESCRIPTION,
    deadline = TODO_1_DEADLINE
)

fun createTodo2Dto() = TodoDto(
    title = TODO_2_TITLE,
    description = TODO_2_DESCRIPTION,
    deadline = TODO_2_DEADLINE
)
