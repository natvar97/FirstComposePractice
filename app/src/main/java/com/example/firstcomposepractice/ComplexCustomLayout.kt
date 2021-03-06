package com.example.firstcomposepractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.example.firstcomposepractice.ui.theme.FirstComposePracticeTheme
import com.google.android.material.chip.Chip
import kotlin.math.max

class ComplexCustomLayout : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstComposePracticeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    BodyContentView()
                }
            }
        }
    }
}

@Composable
fun Greeting3(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun StaggeredGrid(
    modifier: Modifier = Modifier,
    rows: Int = 3,
    content: @Composable () -> Unit,
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        val rowWidths = IntArray(rows) { 0 }
        val rowHeights = IntArray(rows) { 0 }

        val placeables = measurables.mapIndexed { index, measurable ->
            val placeable = measurable.measure(constraints)

            val row = index % rows
            rowWidths[row] += placeable.width
            rowHeights[row] = max(rowHeights[row], placeable.height)
            placeable
        }

        val width =
            rowWidths.maxOrNull()?.coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth))
                ?: constraints.minWidth

        val height = rowHeights.sumOf { it }
            .coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))

        val rowY = IntArray(rows) { 0 }
        for (i in 1 until rows) {
            rowY[i] = rowY[i - 1] + rowHeights[i - 1]
        }

        layout(width, height) {
            val rowX = IntArray(rows) { 0 }
            placeables.forEachIndexed { index, placeable ->
                val row = index % rows
                placeable.placeRelative(
                    x = rowX[row],
                    y = rowY[row]
                )
                rowX[row] += placeable.width
            }

        }
    }
}

@Composable
fun Chip(
    modifier: Modifier = Modifier,
    text: String
) {
    Card(
        modifier = modifier,
        border = BorderStroke(color = Color.Black, width = Dp.Hairline),
        shape = RoundedCornerShape(5.dp)
    ) {
        Row(
            modifier = Modifier.padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp, 16.dp)
                    .background(color = MaterialTheme.colors.secondary)
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Text(text = text)
        }
    }
}

val topics = listOf(
    "Arts & Crafts", "Beauty", "Books", "Business", "Comics", "Culinary",
    "Design", "Fashion", "Film", "History", "Maths", "Music", "People", "Philosophy",
    "Religion", "Social sciences", "Technology", "TV", "Writing"
)

@Composable
fun BodyContentView(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(color = Color.LightGray)
            .padding(16.dp)
            .size(200.dp)
            .horizontalScroll(rememberScrollState()),
        content = {
            StaggeredGrid(modifier = modifier, rows = 5) {
                for (topic in topics) {
                    Chip(text = topic, modifier = modifier.padding(8.dp))
                }
            }
        }
    )
}

//creating the modifier
@Stable
fun Modifier.padding(all: Dp) =
    this then PaddingModifier(
        start = all,
        top = all,
        end = all,
        bottom = all,
        rtlAware = true
    )



@Preview(showBackground = true)
@Composable
fun DefaultPreview6() {
    FirstComposePracticeTheme {
        BodyContentView()
    }
}

private class PaddingModifier(
    private var start: Dp = 0.dp,
    private var top: Dp = 0.dp,
    private var end: Dp = 0.dp,
    private var bottom: Dp = 0.dp,
    private var rtlAware: Boolean
) : LayoutModifier {
    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {
        val horizontal = start.roundToPx() + end.roundToPx()
        val vertical = top.roundToPx() + bottom.roundToPx()

        val placeable = measurable.measure(constraints.offset(-horizontal, -vertical))

        val width = constraints.constrainWidth(placeable.width + horizontal)
        val height = constraints.constrainHeight(placeable.height + vertical)
        return layout(width, height) {
            if (rtlAware) {
                placeable.placeRelative(start.roundToPx(), top.roundToPx())
            } else {
                placeable.place(start.roundToPx(), top.roundToPx())
            }
        }
    }
}