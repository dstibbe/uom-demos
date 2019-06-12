package tech.uom.demo

class ExpressionTokenizer {

    fun tokenize(input: String): List<Token> {
        return input.split(" ").map { Token(it) }
    }
}