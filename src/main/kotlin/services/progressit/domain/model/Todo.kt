package services.progressit.domain.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import org.hibernate.annotations.UuidGenerator
import java.time.OffsetDateTime

@Entity
@Suppress("LateinitUsage")
class Todo {
    @Id
    @GeneratedValue
    @UuidGenerator
    var id: String? = null

    lateinit var title: String
    lateinit var description: String
    lateinit var deadline: OffsetDateTime
}
