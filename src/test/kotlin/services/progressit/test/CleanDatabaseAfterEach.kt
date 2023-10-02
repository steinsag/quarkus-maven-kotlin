package services.progressit.test

import jakarta.inject.Inject
import org.junit.jupiter.api.AfterEach
import services.progressit.domain.persistence.TodoRepository

open class CleanDatabaseAfterEach {
    @Inject
    lateinit var todoRepository: TodoRepository

    @AfterEach
    fun afterEach() {
        todoRepository.deleteAll()
    }
}
