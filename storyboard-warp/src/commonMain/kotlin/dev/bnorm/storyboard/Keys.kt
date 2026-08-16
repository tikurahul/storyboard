package dev.bnorm.storyboard

import androidx.collection.mutableScatterMapOf

class Keys {
    /** The stable keys for all the different scenes in a story board. */
    internal val keyMap = mutableScatterMapOf<Token, Int>()

    /**
     * Assigns a new stable `contentId` for a given [Token] instance.
     */
    fun assignKey(token: Token) {
        var count = keyMap[token] ?: 0
        count += 1
        val key = "$token#$count"
        token.assignKey(newKey = key)
        keyMap[token] = count
    }
}
