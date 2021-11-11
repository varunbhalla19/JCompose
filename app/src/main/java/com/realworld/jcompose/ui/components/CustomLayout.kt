package com.realworld.jcompose.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.realworld.jcompose.ui.theme.ComposeTestTheme
import kotlin.random.Random

/*

- Use the layout modifier to manually control how to measure and position an element

-you can only measure your children once.

-measurable: child to be measured and placed
-constraints: minimum and maximum for the width and height of the child

- Measure the composable by calling measurable.measure(constraints)

-When calling measure(constraints), you can pass in the given constraints of the composable available in the constraints lambda parameter or create your own...

-The result of a measure() call on a Measurable is a Placeable that can be positioned by calling placeRelative(x, y)
- If you don't call placeRelative, the composable won't be visible. placeRelative automatically adjusts the position of the placeable based on the current layoutDirection.

-  you can use the Layout composable to manually control how to measure and position the layout's children.


*/


fun Modifier.firstBaselineToTop(units: Dp) =
    this.then(
        layout { measurable, constraints ->
            val placeable = measurable.measure(constraints)

            check(placeable[FirstBaseline] != AlignmentLine.Unspecified)

            val firstBaseLine = placeable[FirstBaseline]

            val placeableY = units.roundToPx() - firstBaseLine
            val height = placeable.height + placeableY

            layout(placeable.width, height){
                placeable.placeRelative(0, placeableY)
            }

        }
    )


@Composable
fun TheTestLayout(){
    CustomColumn(
        Modifier.padding(8.dp)
    ) {
        Text(text = "Hello")
        Text(text = "Custom", Modifier.padding(8.dp))
        Text(text = "Column")
    }
}


@Composable
fun CustomColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
){
    Layout(
        content = content,
        modifier = modifier,
        measurePolicy = {
            measurables, constraints ->
                //  the first thing to do is measure our children that can only be measured once

                val placeables = measurables.map { measurable ->
                    measurable.measure(constraints)              // Measure each child
                }
                var positionY = 0 // Track the y co-ord we have placed children up to

                // Specify the size of our own Column by calling the layout(width, height) method,
                // Set the size of the layout as big as it can

                layout(constraints.maxWidth, constraints.maxHeight){
                    //  position our children on the screen by calling placeable.placeRelative(x, y). In order to place the children vertically,
                    //  we keep track of the y coordinate we have placed children up to

                    placeables.forEach { placeable ->
                        placeable.placeRelative(0,positionY)
                        positionY += placeable.height
                    }

                }
        }
    )
}


@Preview(showBackground = true)
@Composable
private fun CustomPreview() {
    ComposeTestTheme {
        TheTestLayout()
    }
}


