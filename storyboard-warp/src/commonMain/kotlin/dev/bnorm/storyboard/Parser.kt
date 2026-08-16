package dev.bnorm.storyboard


/**
 * A custom parser if you wanted full control on how to parse tokens.
 */
interface Parser {
    /**
     * Parses a multi-line code snippet into a sequence of [Token]s.
     */
    fun parse(code: String): List<Token>
}

expect fun parse(code: String, language: Language): List<Token>
