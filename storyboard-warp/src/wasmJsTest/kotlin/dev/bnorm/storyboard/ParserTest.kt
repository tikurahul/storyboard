package dev.bnorm.storyboard

import kotlin.test.Test

class ParserTest {
    @Test
    fun testParseTokens() {
        val tokens = parse(
            code = """
                val x = 10
                println(x)
            """.trimIndent(),
            language = Kotlin
        )
        println(tokens)
    }

    @Test
    fun testParseTokensWithEmptyLines() {
        val tokens = parse(
            code = """
              import androidx.tracing.Tracer

              fun main() {

              }
            """.trimIndent(),
            language = Kotlin
        )
        tokens.forEach { println(it) }
    }
}
