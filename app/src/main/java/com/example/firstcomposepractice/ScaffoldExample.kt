package com.example.firstcomposepractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.firstcomposepractice.ui.theme.FirstComposePracticeTheme
import com.google.accompanist.coil.rememberCoilPainter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class ScaffoldExample : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstComposePracticeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    ScaffoldExampleLayout()
                }
            }
        }
    }
}

@Composable
fun ScaffoldExampleLayout() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Scaffold Practice")
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Filled.Favorite, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->
        BodyContent(
            modifier = Modifier
                .padding(innerPadding)
                .padding(8.dp)
        )
    }
}

@Composable
fun BodyContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(8.dp)) {
        Text(text = "Hi There")
        Text(text = "I have started Compose learning")
        Spacer(modifier = Modifier.padding(vertical = 20.dp))
        ImageLazyList()
    }

}

//this is a simple list

@Composable
fun SimpleList() {

    val scrollState = rememberScrollState()

    Column(modifier = Modifier.verticalScroll(scrollState)) {
        repeat(100) {
            Text(text = "Item - $it")
        }
    }
}


@Composable
fun ImageLazyList() {
    val listSize = 100
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Row {
        Button(onClick = {
            coroutineScope.launch {
                scrollState.animateScrollToItem(0)
            }
        })
        {
            Text(text = "Scroll To Top")
        }

        Button(onClick = {
            coroutineScope.launch {
                scrollState.animateScrollToItem(listSize - 1)
            }
        }) {
            Text(text = "Scroll To Bottom")
        }
    }

    LazyColumn(state = scrollState) {
        items(100) {
            ImageListItem(index = it)
        }

    }

}


@Composable
fun ImageListItem(index: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = rememberCoilPainter(
                request = "https://developer.android.com/images/brand/Android_Robot.png"
            ),
            contentDescription = "Android Logo",
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = "Item - $index", style = MaterialTheme.typography.subtitle1)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    FirstComposePracticeTheme {
        ScaffoldExampleLayout()
    }
}