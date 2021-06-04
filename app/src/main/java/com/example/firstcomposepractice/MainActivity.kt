package com.example.firstcomposepractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.firstcomposepractice.ui.theme.FirstComposePracticeTheme
import org.w3c.dom.NameList
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                NewsStory()
            }
        }
    }

}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    FirstComposePracticeTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            content()
        }
    }
}

@Composable
fun NewsStory(
    names: List<String> = listOf(
        "A day wandering through the sandhills " +
                "in Shark Fin Cove, and a few of the " +
                "sights I saw",
        "Davenport , California",
        "June 2021"
    )
) {
    MaterialTheme {
        val typography = MaterialTheme.typography
        val counterState = remember {
            mutableStateOf(0)
        }
        val list: List<String> = List(100) { "Hello Jetpack Compose #$it" }
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxHeight(),
        ) {
            Image(
                painter = painterResource(id = R.drawable.pexels_pixabay_247431),
                contentDescription = null,
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(20.dp))

            for (name in names) {
                if (name == names[0]) {
                    Text(
                        text = name,
                        style = typography.h6,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                } else {
                    Text(text = name, style = typography.body2)
                }
            }


            /*
                Text(
                    text = "A day wandering through the sandhills " +
                            "in Shark Fin Cove, and a few of the " +
                            "sights I saw",
                    style = typography.h6,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Davenport , California",
                    style = typography.body2
                )
                Text(
                    text = "June 2021",
                    style = typography.body2
                )
            */
            Spacer(modifier = Modifier.height(20.dp))

            NameList(
                names = list, modifier = Modifier
                    .weight(1f)
                    .padding(10.dp)
                    .fillMaxHeight()
            )

            Counter(
                count = counterState.value,
                updateCount = { newCount ->
                    counterState.value = newCount
                }
            )
        }
    }
}

@Composable
fun Greetings(name: String) {

    var isSelected by remember {
        mutableStateOf(false)
    }
    val backgroundColor by animateColorAsState(
        if (isSelected) Color.Red else Color.Transparent
    )

    Text(
        text = name, modifier = Modifier
            .padding(24.dp)
            .background(color = backgroundColor)
            .clickable(onClick = { isSelected = !isSelected })
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FirstComposePracticeTheme {
        MyApp {
            NewsStory()
        }
    }
}

@Composable
fun Counter(count: Int, updateCount: (Int) -> Unit) {
    Button(
        onClick = { updateCount(count + 1) },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (count > 5) Color.Green else Color.Cyan
        )
    ) {
        Text(text = "I have been clicked the button $count times")
    }
}

@Composable
fun NameList(names: List<String>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(items = names) { name ->
            Greetings(name = name)
            Divider(color = Color.Black, modifier = Modifier.padding(0.dp, 10.dp))
        }
    }
}