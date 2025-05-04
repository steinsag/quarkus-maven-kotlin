package services.progressit.domain.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import org.hibernate.annotations.GenericGenerator
import java.time.OffsetDateTime

private const val UUID_GENERATOR_ID = "UUID"

@Entity
@Suppress("LateinitUsage")
class Todo {
    @Id
    @GeneratedValue(generator = UUID_GENERATOR_ID)
    @GenericGenerator(
        name = UUID_GENERATOR_ID,
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    var id: String? = null

    lateinit var title: String
    lateinit var description: String
    lateinit var deadline: OffsetDateTime
}
