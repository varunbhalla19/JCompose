package com.realworld.jcompose.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.realworld.jcompose.ui.theme.ComposeTestTheme

@Composable
fun MyApp(){

    var shouldShowOnboarding by remember { mutableStateOf(true) } // rememberSavable to survive config changes.

    if(shouldShowOnboarding){
        Onboarding{
            shouldShowOnboarding = false
        }
    }else {
        Greetings()
    }
}

@Composable
fun Onboarding(
    onPress: () -> Unit = {}
){

    Surface{
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(onClick = {
                onPress()
            }) {
                Text(text = "OnBoarding Screen")
            }

        }
    }
}


@Composable
fun Greetings(){

    val namesList = List(1000){ "Test: $it" }
    LazyColumn(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxSize(),
    ){
        items(namesList){
            name -> Greeting(name = name)
        }
    }

}

@Composable
fun Greeting(name: String){
    Card(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier.padding(8.dp, 4.dp)
    ) {

        var expanded by remember{
            mutableStateOf(false)
        }   // use rememberSaveable to persist recomposition by lazyColumn

        Row(
            modifier = Modifier
                .padding(8.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .weight(1f, true)
            ) {
                Text(
                    text = "Hello,"
                )
                Text(
                    text = name,
                    style = MaterialTheme.typography.h4.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )

                if (expanded) {
                    Text(
                        text = ("Composem ipsum color sit lazy, " +
                                "padding theme elit, sed do bouncy. ").repeat(4),
                    )
                }

            }

            OutlinedButton(
                onClick = {
                    expanded = !expanded
                }
            ) {
                Text(text = if (expanded) "Show Less" else "Show More")
            }
        }



    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
private fun DefaultPreview() {
    ComposeTestTheme {
        MyApp()
    }
}