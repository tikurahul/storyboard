package dev.bnorm.storyboard

/**
 * The [Token] that will be passed to the diffing algorithm to find structural similarities.
 *
 * We are using a combination of the [content], and the `primary` [scope] + its [depth] to find
 * `anchor`s.
 */
class Token(
    /** The actual content of the parsed token. */
    val content: String,
    /** The primary scope */
    val scope: String,
    /** The depth of the primary scope. */
    val depth: Int,
    /* More context for animations and rendering. */
    val language: Language,
    val allScopes: List<String>,
    val lineNumber: Int,
    val startIndex: Int,
    val endIndex: Int
) {
    /** The underlying unique key that was assigned to the token.
     * This is guaranteed to be stable across a story board. */
    private var key: String? = null

    fun hasKey(): Boolean {
        return key != null
    }

    fun assignKey(newKey: String) {
        val key = key
        check(key == null) { "Cannot override `key` for $this" }
        this.key = newKey
    }

    fun key(): String {
        val contentId = key
        check(contentId != null) { "`key` was not assigned to $this" }
        return contentId
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Token) return false
        if (depth != other.depth) return false
        if (content != other.content) return false
        if (scope != other.scope) return false

        return true
    }

    override fun hashCode(): Int {
        var result = depth
        result = 31 * result + content.hashCode()
        result = 31 * result + scope.hashCode()
        return result
    }

    override fun toString(): String {
        return "Token(content='$content', scope='$scope', depth=$depth, lineNumber=$lineNumber, startIndex=$startIndex, endIndex=$endIndex)"
    }
}
