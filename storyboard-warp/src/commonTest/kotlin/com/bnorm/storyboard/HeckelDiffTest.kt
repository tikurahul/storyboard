package com.bnorm.storyboard

import dev.bnorm.storyboard.Json
import dev.bnorm.storyboard.Kotlin
import dev.bnorm.storyboard.diff
import dev.bnorm.storyboard.parse
import org.intellij.lang.annotations.Language
import kotlin.test.Test

class HeckelDiffTest {
    @Test
    fun basicDiff() {
        @Language("kotlin")
        val slideP = """
            val x = 10
        """.trimIndent()

        @Language("kotlin")
        val slideC = """
            val y = 10
        """.trimIndent()

        val previous = parse(code = slideP, language = Kotlin)
        val current = parse(code = slideC, language = Kotlin)
        val changes = diff(previous = previous, current = current)
        changes.forEach { change ->
            println(change)
        }
    }

    @Test
    fun basicDiff2() {
        @Language("kotlin")
        val slideP = """
            val x = 10
        """.trimIndent()

        @Language("kotlin")
        val slideC = """
            val x = 20
        """.trimIndent()

        val previous = parse(code = slideP, language = Kotlin)
        val current = parse(code = slideC, language = Kotlin)
        val changes = diff(previous = previous, current = current)
        changes.forEach { change ->
            println(change)
        }
    }

    @Test
    fun lineMoveDiff() {
        @Language("kotlin")
        val slideP = """
            val x = 10
            fun convert(input: Int) {
              // ...
            }
        """.trimIndent()

        @Language("kotlin")
        val slideC = """
            val x = 10
            fun convert(input: Int) {
              println("The actual implementation")
            }
        """.trimIndent()

        val previous = parse(code = slideP, language = Kotlin)
        val current = parse(code = slideC, language = Kotlin)
        val changes = diff(previous = previous, current = current)
        changes.forEach { change ->
            println(change)
        }
    }

    @Test
    fun lineMoveDiff2() {
        @Language("kotlin")
        val slideP = """
            val a = 10
            val b = 20
            val c = 30
        """.trimIndent()

        @Language("kotlin")
        val slideC = """
            val b = 20
            val c = 30
            val d = 40
        """.trimIndent()

        val previous = parse(code = slideP, language = Kotlin)
        val current = parse(code = slideC, language = Kotlin)
        val changes = diff(previous = previous, current = current)
        changes.forEach { change ->
            println(change)
        }
    }

    @Test
    fun basicJsonDiff() {
        @Language("json")
        val slideP = """
            {
              "a" : 10,
              "b" : [1, 2]
            }
        """.trimIndent()

        @Language("json")
        val slideC = """
            {
              "a" : 10,
              "b" : [1, 2, 3]
            }
        """.trimIndent()

        val previous = parse(code = slideP, language = Json)
        val current = parse(code = slideC, language = Json)
        val changes = diff(previous = previous, current = current)
        changes.forEach { change ->
            println(change)
        }
    }
}
