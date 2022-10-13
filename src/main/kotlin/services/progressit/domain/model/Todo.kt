package services.progressit.domain.model

import org.hibernate.annotations.GenericGenerator
import java.time.OffsetDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

private const val UUID_GENERATOR_ID = "UUID"

@Entity
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
