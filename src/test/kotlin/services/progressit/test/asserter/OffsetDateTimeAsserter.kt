package services.progressit.test.asserter

import org.assertj.core.api.AbstractAssert
import org.assertj.core.api.Assertions
import java.time.OffsetDateTime

class OffsetDateTimeAsserter(actual: OffsetDateTime) :
    AbstractAssert<OffsetDateTimeAsserter, OffsetDateTime>(actual, OffsetDateTimeAsserter::class.java) {

    fun isEqualTo(expected: OffsetDateTime) {
        Assertions.assertThat(actual).usingDefaultComparator().isEqualTo(expected)
    }

    companion object {
        fun assertThat(actual: String) = OffsetDateTimeAsserter(OffsetDateTime.parse(actual))
    }
}
