package services.progressit.test

import org.junit.jupiter.api.AfterEach
import services.progressit.domain.persistence.TodoRepository
import javax.inject.Inject

open class CleanDatabaseAfterEach {

    @Inject
    lateinit var todoRepository: TodoRepository

    @AfterEach
    fun afterEach() {
        todoRepository.deleteAll()
    }
}
