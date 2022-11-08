package services.progressit.test.asserter

import java.time.OffsetDateTime

class OffsetDateTimeComparator : Comparator<OffsetDateTime> {
    override fun compare(o1: OffsetDateTime, o2: OffsetDateTime): Int {
        return o1.toInstant().compareTo(o2.toInstant())
    }
}
