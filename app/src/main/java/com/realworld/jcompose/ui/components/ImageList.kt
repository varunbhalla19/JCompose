package com.realworld.jcompose.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.realworld.jcompose.ui.theme.ComposeTestTheme
import kotlinx.coroutines.launch

@Composable
fun ProfileCard(){
    Row(
        Modifier
            .background(Color.LightGray)
            .padding(8.dp)
            .clip(RoundedCornerShape(2.dp))
            .background(MaterialTheme.colors.surface)
            .clickable {}
            .padding(8.dp)
    ) {
        Surface(
            modifier = Modifier.size(48.dp),
            shape = CircleShape,
            color = Color.Yellow
        ) {}
        ProfileDetail(
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun ProfileDetail(modifier: Modifier = Modifier){
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Varun Bhalla",
            fontWeight = FontWeight.Bold
        )
        CompositionLocalProvider(LocalContentAlpha.provides(ContentAlpha.medium)) {
            Text(
                text = "3 minutes ago",
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@Composable
fun ImageListLayout(){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Hello")
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.Favorite, contentDescription = null)
                    }
                }
            )
        }
    ) {
        LazilyList()
    }
}

@Composable
fun SimpleList() {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.verticalScroll(scrollState)
    ) {
        repeat(100) {
            Text("Item #$it")
        }
    }
}

@Composable
fun LazilyList(){

    val listSize = 1000
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()


    Column() {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = {
                coroutineScope.launch {
                    scrollState.animateScrollToItem(0)
                }
            }) {
                Text(text = "Scroll to Top")
            }
            Button(onClick = {
                coroutineScope.launch {
                    scrollState.animateScrollToItem(listSize-1)
                }
            }) {
                Text(text = "Scroll to Bottom")
            }
        }

        LazyColumn( state = scrollState, modifier = Modifier.fillMaxWidth() ) {
            items(listSize){
//            Text(text = "Lazy Item: $it")
                ImageListItem(index = it)
            }
        }

    }

}

@Composable
fun ImageListItem(index: Int){
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        Image(
            painter = rememberImagePainter(
                data = "https://robohash.org/${index}.png?set=set4"
            ),
            contentDescription = "Item Image",
            modifier = Modifier.size(50.dp)
        )
        Text(text = "Item: $index", style = MaterialTheme.typography.h4)
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfilePreview() {
    ComposeTestTheme {
//        ProfileCard()
        ImageListLayout()
    }
}


//CompositionLocalProvider. It allows us to pass data implicitly through the composition tree. In this case, we're accessing ContentAlpha.medium, the medium opacity level, which is defined at the theme level