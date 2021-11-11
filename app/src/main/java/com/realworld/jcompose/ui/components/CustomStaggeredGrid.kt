package com.realworld.jcompose.ui.components

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.realworld.jcompose.ui.theme.ComposeTestTheme
import kotlin.math.max

@Composable
fun StaggeredGrid(
    modifier: Modifier = Modifier,
    rows: Int,
    content: @Composable () -> Unit
){

    Layout(
        modifier = modifier,
        content = content,
        measurePolicy = { measurables, constraints ->

            val rowWidths = IntArray(rows){ 0 }  // To keep track of the width of each row

            val rowHeights = IntArray(rows){ 0 }  // To keep track of the height of each row

            // Don't constrain child views further, measure them with given constraints
            val placeables = measurables.mapIndexed { index, measurable ->


                // Measure each child
                val placeable = measurable.measure(constraints)

                // Track the width and max height of each row

                val row = index % rows
                rowWidths[row] += placeable.width
                rowHeights[row] = max(rowHeights[row], placeable.height)

                placeable

            }

            Log.d("Custom", """
                measurables: ${measurables.size}
                constraints widths: ${constraints.minWidth} / ${constraints.maxWidth}
                constraints heights: ${constraints.minHeight} / ${constraints.maxHeight}
                total rowWidth: ${rowWidths} => ${rowWidths.sumOf { it }}
                total rowHeight: ${rowHeights} => ${rowHeights.sumOf { it }}
            """.trimIndent())

            // Grid's width is the widest row
            val gridWidth = rowWidths.maxOrNull()?.coerceIn(
                constraints.minWidth..constraints.maxWidth
            ) ?: constraints.minWidth

            // Grid's height is the sum of each row's height
            val gridHeight = rowHeights.sumOf { it }.coerceIn(
                constraints.minHeight..constraints.maxHeight
            )

            // y co-ord of each row
            val rowY = IntArray(rows) { 0 }

            for (i in 1 until rows) {
                rowY[i] = rowY[i-1] + rowHeights[i-1] // current Y = prev Y + height (prev) Y
            }

            layout(gridWidth, gridHeight){

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
    )
}

val topics = listOf(
    "Arts & Crafts", "Beauty", "Books", "Business", "Comics", "Culinary",
    "Design", "Fashion", "Film", "History", "Maths", "Music", "People", "Philosophy",
    "Religion", "Social sciences", "Technology", "TV", "Writing"
)


@Composable
fun ChipCard(
    text: String,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier.clickable {  } ,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(Dp.Hairline, Color.Black)
    ) {
        Row(
            modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier
                .size(16.dp, 16.dp)
                .background(color = MaterialTheme.colors.secondary)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = text)
        }
    }
}

@Composable
fun BodyContent(
    modifier: Modifier = Modifier
){

    val scrollState = rememberScrollState()

    BlankScreen() {
        StaggeredGrid(
            rows = 4,
            modifier = Modifier
                .horizontalScroll(scrollState)
                .padding(top = 16.dp)
                .background(Color.Magenta)
        ) {
            topics.forEach {
                ChipCard(text = it, modifier = Modifier.padding(4.dp))
            }
        }
    }

}

@Preview
@Composable
fun GridPreview(){
    ComposeTestTheme {
        BodyContent()
    }
}

@Preview
@Composable
fun ChipPreview(){
    ComposeTestTheme {
        ChipCard(text = "Varun")
    }
}

