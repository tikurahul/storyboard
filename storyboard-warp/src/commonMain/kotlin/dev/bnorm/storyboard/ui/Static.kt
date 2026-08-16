package dev.bnorm.storyboard.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import dev.bnorm.storyboard.DiffContents
import dev.bnorm.storyboard.State
import dev.bnorm.storyboard.Token
import dev.bnorm.storyboard.afterViewOf
import dev.bnorm.storyboard.content

@Composable
fun Static(
    modifier: Modifier,
    tokens: List<Token>,
) {
    val rows: List<List<State>> = remember(tokens) { tokens.asDiffContents().rows }
    Column(modifier = modifier.fillMaxSize()) {
        rows.forEach { row ->
            Row {
                row.forEach { state ->
                    Text(
                        text = state.content(),
                        color = state.color(),
                        fontFamily = FontFamily.Monospace,
                        fontSize = DEFAULT_FONT_SIZE.sp,
                        lineHeight = DEFAULT_LINE_HEIGHT.sp,
                    )
                }
            }
        }
    }
}

private fun List<Token>.asDiffContents(): DiffContents {
    val inserts = mapIndexed { index, token -> State.Insert(token = token, index = index) }
    return afterViewOf(states = inserts)
}
