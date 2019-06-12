package tech.uom.demo

import org.junit.Assert.assertEquals
import org.junit.Test

class ExpressionTokenizerTest {

    @Test
    fun `can tokenize the input`() {
        ExpressionTokenizer().tokenize("2 m").run {
            assertEquals("Expected 2 tokens", size, 2)
            assertEquals("Expected 2 tokens", get(0), Token("2"))
            assertEquals("Expected 2 tokens", get(1), Token("m"))
        }
    }


}