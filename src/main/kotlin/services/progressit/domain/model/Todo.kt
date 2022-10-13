package services.progressit.domain.model

import java.time.OffsetDateTime

data class Todo(
    val title: String,
    val description: String,
    val deadline: OffsetDateTime
)
