package com.realworld.jcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.realworld.jcompose.data.SampleData
import com.realworld.jcompose.ui.theme.ComposeTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeTestTheme {
                Conversation(messages = SampleData.conversationSample)
            }
        }
    }
}

data class Message( val main: String, val body: String = "" )

@Composable
fun MessageContainer(msg: Message) {
    Row(
        modifier = Modifier.padding(8.dp)
    ){
        Image(
            painter = painterResource(id = R.drawable.pf1),
            modifier = Modifier
                .size(40.dp)
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
                .fillMaxSize()
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            contentDescription = "Simple Image"
        )
        Spacer(modifier = Modifier.width(8.dp))

        var isExpanded by remember { mutableStateOf(false) }

        val bgColor: Color by animateColorAsState(
            if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
        )

        Column(
            modifier = Modifier.clickable{
                isExpanded = !isExpanded
            }
        ) {
            Text(
                text = msg.main,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2
            )
            Spacer(modifier = Modifier.height(4.dp))
            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 1.dp,
                color = bgColor,
                modifier = Modifier.animateContentSize().padding(1.dp)
            ) {
                Text(
                    text = msg.body,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(4.dp),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1
                )
            }

        }
    }
}

@Composable
fun Conversation(messages: List<Message>){
    LazyColumn{
        items(
            items = messages,
            itemContent = {
                item: Message -> MessageContainer(item)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeTestTheme {
        Conversation(SampleData.conversationSample)
    }


}