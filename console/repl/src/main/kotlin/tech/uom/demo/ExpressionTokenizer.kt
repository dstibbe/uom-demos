package tech.uom.demo

class ExpressionTokenizer {

    fun tokenize(input: String): List<Token> {
        return input.trim().split(regex).map { Token(it) }
    }

    companion object {
        private val regex = """\s+""".toRegex()
    }
}