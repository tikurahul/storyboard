package dev.bnorm.storyboard.ui

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import dev.bnorm.storyboard.DiffContents
import dev.bnorm.storyboard.State
import dev.bnorm.storyboard.Token
import dev.bnorm.storyboard.content


@Composable
internal fun Code(
    diffContents: DiffContents,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier
) {
    val rows = diffContents.rows
    Column(modifier = modifier.fillMaxSize()) {
        rows.forEach { states: List<State> ->
            Row {
                states.forEach { state ->
                    when (state) {
                        is State.Match -> {
                            with(receiver = sharedTransitionScope) {
                                Text(
                                    text = state.content(),
                                    color = state.color(),
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = DEFAULT_FONT_SIZE.sp,
                                    lineHeight = DEFAULT_LINE_HEIGHT.sp,
                                    modifier = Modifier.sharedElement(
                                        sharedContentState = rememberSharedContentState(key = state.sharedKey()),
                                        animatedVisibilityScope = animatedVisibilityScope,
                                        boundsTransform = boundsTransform
                                    )
                                )
                            }
                        }

                        is State.Delete,
                        is State.Insert -> {
                            val token: Token = state.token()
                            with(sharedTransitionScope) {
                                with(animatedVisibilityScope) {
                                    Text(
                                        state.content(),
                                        color = state.color(),
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = DEFAULT_FONT_SIZE.sp,
                                        lineHeight = DEFAULT_LINE_HEIGHT.sp,
                                        modifier = Modifier
                                            .sharedElement(
                                                sharedContentState = rememberSharedContentState(key = token.key()),
                                                animatedVisibilityScope = animatedVisibilityScope,
                                                boundsTransform = boundsTransform
                                            )
                                            .renderInSharedTransitionScopeOverlay()
                                            .animateEnterExit(
                                                enter = fadeIn(animationSpec = tween(durationMillis = TWEEN_DURATION_MS)),
                                                exit = fadeOut(animationSpec = tween(durationMillis = TWEEN_DURATION_MS))
                                            )
                                    )
                                }
                            }
                        }

                        State.Empty -> {
                            // Should never happen.
                        }
                    }
                }
            }
        }
    }
}

internal fun State.token(): Token {
    return when (this) {
        is State.Match -> current
        is State.Insert -> token
        is State.Delete -> token
        else -> throw IllegalStateException("Should never happen")
    }
}

internal fun State.Match.sharedKey(): String {
    return if (previous.hasKey() && current.hasKey()) {
        check(previous.key() == current.key()) {
            "Content Id must match for $previous and $current"
        }
        current.key()
    } else {
        // The next best thing is to return an identifier that is automatically unique
        // between 2 slides (but not across a deck).
        "Match('$previousIdx' -> '$currentIdx')"
    }
}
