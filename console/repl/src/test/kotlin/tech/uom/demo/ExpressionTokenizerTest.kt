package tech.uom.demo

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.core.IsCollectionContaining
import org.hamcrest.core.IsCollectionContaining.*
import org.junit.Assert.assertThat
import org.junit.Test

class ExpressionTokenizerTest {

    @Test
    fun `can tokenize the input`() {
        ExpressionTokenizer().tokenize("            2     m    +     4    kasd             ").run {
            assertThat(size, `is`(5))
            assertThat(this, hasItems(
                    Token("2")
                    , Token("m")
                    , Token("+")
                    , Token("4")
                    , Token("kasd")
            ))

        }
    }


}