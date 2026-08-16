package dev.bnorm.storyboard

import androidx.compose.runtime.Immutable

sealed class State {
    /** Represents the initial state. */
    object Empty : State()

    /** This represents a Match. */
    @Immutable
    data class Match(
        val previous: Token,
        val previousIdx: Int,
        val current: Token,
        val currentIdx: Int
    ) : State() {
        fun content() = current.content
    }

    /** Represents an insert. */
    @Immutable
    data class Insert(val token: Token, val index: Int) : State()

    /** Represents a deletion. */
    @Immutable
    data class Delete(val token: Token, val index: Int) : State()
}

fun State.content(): String {
    return when (this) {
        is State.Match -> content()
        is State.Delete -> token.content
        is State.Insert -> token.content
        is State.Empty -> throw IllegalStateException("Should never happen")
    }
}
