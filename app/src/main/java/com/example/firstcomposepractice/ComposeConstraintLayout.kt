package com.example.firstcomposepractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.firstcomposepractice.ui.theme.FirstComposePracticeTheme
import java.lang.reflect.Modifier

class ComposeConstraintLayout : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstComposePracticeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    ConstraintLayoutContent()
                }
            }
        }
    }
}

@Composable
fun Greeting4(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun ConstraintLayoutContent() {
    ConstraintLayout {
        val (button, text) = createRefs()

        Button(
            onClick = { },
            modifier = androidx.compose.ui.Modifier.constrainAs(button) {
                top.linkTo(parent.top, margin = 16.dp)
            }
        ) {
            Text(text = "Button")
        }
        Text(text = "Text", modifier = androidx.compose.ui.Modifier.constrainAs(text) {
            top.linkTo(button.bottom, margin = 16.dp)
            centerHorizontallyTo(parent)
        })

    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview7() {
    FirstComposePracticeTheme {
        ConstraintLayoutContent()
    }
}