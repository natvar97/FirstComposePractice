package com.example.firstcomposepractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.firstcomposepractice.ui.theme.FirstComposePracticeTheme

class ExtendModifier : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstComposePracticeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }
        }
    }
}


//extending the Modifier with firstBaseLineToTop method
fun Modifier.firstBaseLineToTop(
    firstBaseLineToTop: Dp
) = this.then(
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)

        check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
        val firstBaseLine = placeable[FirstBaseline]

        val placeableY = firstBaseLineToTop.roundToPx() - firstBaseLine
        val height = placeable.height + placeableY
        layout(placeable.width, height) {
            placeable.placeRelative(0, placeableY)
        }
    }
)
/*
this example shows difference between firstBaseLineToTop and paddingTop

Text(text = "Hello $name!", Modifier.firstBaseLineToTop(32.dp))
 */


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!", Modifier.padding(top = 32.dp))
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview4() {
    FirstComposePracticeTheme {
        Greeting("Android")
    }
}