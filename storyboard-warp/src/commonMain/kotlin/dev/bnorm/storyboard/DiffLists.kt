package dev.bnorm.storyboard

import androidx.compose.runtime.Immutable

/**
 * Represents a Scene that contains [code] among other things.
 */
@Immutable
interface CodeBlock {
    /**@return the `code` snippet in the scene. This is a multi-line [String].  */
    fun code(): String
}

@Immutable
data class BasicCode(val code: String) : CodeBlock {
    override fun code(): String {
        return code
    }
}

@Immutable
data class DiffContents(val rows: List<List<State>>)

/**
 * Builds a presentation for a group of slides that contain code [codeBlocks].
 */
fun buildDiffList(codeBlocks: List<CodeBlock>, language: Language): List<DiffContents> {
    if (codeBlocks.isEmpty()) return emptyList()
    val keys = Keys()
    val diffResult = mutableListOf<DiffContents>()
    val parsed = codeBlocks.map { snippet -> parse(code = snippet.code(), language) }
    // Assign keys for all the tokens we see in the first slide.
    // For every subsequent match, we propagate the existing token ids.
    // New ids are only generated when we see new inserts.
    parsed.first().forEach { token ->
        keys.assignKey(token)
    }
    val parsedPairs = parsed.zipWithNext()
    parsedPairs.forEachIndexed { index, (previous, current) ->
        val states = diff(previous, current, keys)
        if (index == 0) {
            diffResult += beforeViewOf(states)
            diffResult += afterViewOf(states)
        } else {
            // For later slides only build the incremental state
            diffResult += afterViewOf(states)
        }
    }
    return diffResult
}


internal fun afterViewOf(states: List<State>): DiffContents {
    // This is the after state.
    // This means we only show inserts and matches.
    val filtered = states.filter { state -> state is State.Match || state is State.Insert }
    // Group by the line
    val rows: Map<Int, List<State>> = filtered.groupBy { state ->
        when (state) {
            is State.Match -> state.current.lineNumber
            is State.Insert -> state.token.lineNumber
            else -> throw IllegalStateException("Should never happen")
        }
    }
    // Sort by the startIndex so they are ordered correctly
    val sortedArray: Array<List<State>> = Array(rows.size) { emptyList() }
    rows.forEach { (rowIdx, states) ->
        val tokens = states.sortedBy { state ->
            when (state) {
                // Pick the previous state for the start index
                is State.Match -> state.current.startIndex
                is State.Insert -> state.token.startIndex
                else -> throw IllegalStateException("Should never happen")
            }
        }
        sortedArray[rowIdx] = tokens
    }
    return DiffContents(rows = sortedArray.asList())
}

internal fun beforeViewOf(states: List<State>): DiffContents {
    // This is the before state.
    // This means we only show deletes, and matches.
    val filtered = states.filter { state -> state is State.Match || state is State.Delete }
    // Group by the line
    val rows: Map<Int, List<State>> = filtered.groupBy { state ->
        when (state) {
            is State.Match -> state.previous.lineNumber
            is State.Delete -> state.token.lineNumber
            else -> throw IllegalStateException("Should never happen")
        }
    }
    // Sort by the startIndex so they are ordered correctly
    val sortedArray: Array<List<State>> = Array(rows.size) { emptyList() }
    rows.forEach { (rowIdx, states) ->
        val tokens = states.sortedBy { state ->
            when (state) {
                // Pick the previous state for the start index
                is State.Match -> state.previous.startIndex
                is State.Delete -> state.token.startIndex
                else -> throw IllegalStateException("Should never happen")
            }
        }
        sortedArray[rowIdx] = tokens
    }
    return DiffContents(rows = sortedArray.asList())
}
