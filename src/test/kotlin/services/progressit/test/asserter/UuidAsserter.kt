package services.progressit.test.asserter

import org.assertj.core.api.AbstractAssert
import org.assertj.core.api.Assertions.assertThatCode
import java.util.*

class UuidAsserter(actual: String?) : AbstractAssert<UuidAsserter, String>(actual, UuidAsserter::class.java) {
    fun isUuid() {
        assertThatCode {
            UUID.fromString(actual)
        }.doesNotThrowAnyException()
    }

    companion object {
        fun assertThat(actualId: String?) = UuidAsserter(actualId)
    }
}
