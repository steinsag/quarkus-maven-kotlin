package services.progressit.test

import java.time.OffsetDateTime

const val TODO_1_ID = "bf71f011-a441-4e6e-ae87-143d215f24f7"
const val TODO_1_TITLE = "Title of todo 1"
const val TODO_1_DESCRIPTION = "Todo 1 is the first todo in the list. It's deadline is in the past."
const val TODO_1_DEADLINE_STRING = "2022-10-01T19:51:37.739+02:00"
val TODO_1_DEADLINE: OffsetDateTime = OffsetDateTime.parse(TODO_1_DEADLINE_STRING)

const val TODO_2_ID = "a5224e6c-b768-4813-9606-73cf2ec18f86"
const val TODO_2_TITLE = "Title of todo 2"
const val TODO_2_DESCRIPTION = "Todo 2 is the second todo in the list. It is due in the future."
const val TODO_2_DEADLINE_STRING = "2032-10-01T19:51:37.739+02:00"
val TODO_2_DEADLINE: OffsetDateTime = OffsetDateTime.parse(TODO_2_DEADLINE_STRING)
