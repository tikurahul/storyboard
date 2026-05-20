package dev.bnorm.storyboard.text.magic

import androidx.compose.ui.text.AnnotatedString

// Tokenize an AnnotatedString into a list of words.
fun AnnotatedString.toWords(): List<AnnotatedString> {
    return buildList {
        var last = 0
        while (last < length) {
            val (i, word) = text.findAnyOf(SYMBOL_WORDS, startIndex = last) ?: break
            if (i > last) addAll(subSequence(last, i).split())
            add(subSequence(i, i + word.length))
            last = i + word.length
        }
        if (last < length) addAll(subSequence(last, length).split())
    }
}

private val SYMBOL_WORDS = setOf(
    "&&",
    "||",
    "==",
    "!=",
    "===",
    "!==",
    ">=",
    "<=",
    "!!",
    "?:",
    "::",
    "->",
    "\"\"\"",
)

private enum class RangeType {
    Word {
        override fun matches(c: Char): Boolean {
            return c.isLetterOrDigit() || c == '_'
        }

    },
    Whitespace {
        override fun matches(c: Char): Boolean {
            return c.isWhitespace()
        }
    },
    ;

    abstract fun matches(c: Char): Boolean
}

private fun AnnotatedString.split(): List<AnnotatedString> {
    return buildList {
        var currentType: RangeType? = null
        var offset = 0
        for (i in this@split.indices) {
            val char = this@split[i]
            if (offset == i) {
                currentType = RangeType.entries.firstOrNull { it.matches(char) }
            } else if (currentType == null || !currentType.matches(char)) {
                if (i > offset) add(subSequence(offset, i))
                add(subSequence(i, i + 1))
                offset = i + 1
            }
        }
        if (offset < length) add(subSequence(offset, length))
    }
}
