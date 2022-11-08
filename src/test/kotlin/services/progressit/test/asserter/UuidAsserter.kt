package services.progressit.test.asserter

import org.assertj.core.api.AbstractAssert
import org.assertj.core.api.Assertions.assertThatCode
import java.util.UUID

class UuidAsserter(actual: String?) : AbstractAssert<UuidAsserter, String>(actual, UuidAsserter::class.java) {

    companion object {
        fun assertThat(actualId: String?) = UuidAsserter(actualId)
    }

    fun isUuid() {
        assertThatCode {
            UUID.fromString(actual)
        }.doesNotThrowAnyException()
    }
}
