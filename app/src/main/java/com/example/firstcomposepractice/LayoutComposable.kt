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
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.firstcomposepractice.ui.theme.FirstComposePracticeTheme

class LayoutComposable : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstComposePracticeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    BodyContentHey()
                }
            }
        }
    }
}

@Composable
fun Greeting2(name: String) {
    Text(text = "Hello $name!")
}

//@Composable
//fun CustomLayout(
//    modifier: Modifier = Modifier,
//    content: @Composable () -> Unit
//) {
//    Layout(modifier = modifier, content = content) { measurables, constraints ->
//        //nothing
//    }
//}

@Composable
fun MyOwnColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(content = content, modifier = modifier) { measurables , constraints ->
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }

        var positionY = 0

        layout(constraints.maxWidth , constraints.maxHeight){
            placeables.forEach { placeable ->
                placeable.placeRelative(x = 0, y = positionY)
                positionY += placeable.height
            }
        }
    }

}

@Composable
fun BodyContentHey(modifier : Modifier = Modifier) {
    MyOwnColumn(modifier.padding(8.dp)) {
        Text(text = "MyOwnColumn")
        Text(text = "place items")
        Text(text = "vertically")
        Text(text = "We've done it with out selves")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview5() {
    FirstComposePracticeTheme {
        BodyContentHey()
    }
}