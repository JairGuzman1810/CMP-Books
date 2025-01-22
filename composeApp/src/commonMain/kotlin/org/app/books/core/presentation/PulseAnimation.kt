package org.app.books.core.presentation

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

/**
 * [PulseAnimation] is a composable function that creates a pulsing animation effect.
 *
 * It uses an infinite transition to animate the scale and alpha of a circular box,
 * creating a visual pulse effect.
 *
 * @param modifier The modifier to be applied to the animation container.
 */
@Composable
fun PulseAnimation(modifier: Modifier = Modifier) {
    // Creates an infinite transition that will continuously animate the progress value.
    val transition = rememberInfiniteTransition()
    // Animates the progress value from 0f to 1f and back to 0f infinitely.
    val progress by transition.animateFloat(
        initialValue = 0f, // The initial value of the animation.
        targetValue = 1f, // The target value of the animation.
        animationSpec = infiniteRepeatable(
            animation = tween(1000), // The animation spec, using a tween with a duration of 1000 milliseconds.
            repeatMode = RepeatMode.Restart // The repeat mode, which is set to restart the animation after it finishes.
        )
    )

    // A Box composable that represents the pulsing circle.
    Box(
        modifier = modifier
            // Applies a graphics layer to the Box to control its scale and alpha.
            .graphicsLayer {
                scaleX = progress // Scales the Box horizontally by the progress value.
                scaleY = progress // Scales the Box vertically by the progress value.
                alpha = 1f - progress // Sets the alpha of the Box to the inverse of the progress value.
            }
            // Adds a border to the Box to make it visible.
            .border(
                width = 5.dp, // The width of the border.
                color = SandYellow, // The color of the border.
                shape = CircleShape // The shape of the border, which is a circle.
            )
    )
}