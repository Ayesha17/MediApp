package com.ayesha.mediapp.ui.common

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ayesha.mediapp.R

@Composable
fun BaseAnimatedLoader(
    backgroundColor: Color = Color.Black.copy(0.4f),
    alphaValue: Float = 0.3f
) {
    Box(
        modifier = Modifier.fillMaxSize().clickable { /* Consume clicks to prevent interaction */ }
    ) {
        // Add a semi-transparent overlay to dim the background
        val backgroundWithAlpha = backgroundColor.copy(alpha = alphaValue)
        Box(
            modifier =
            Modifier
                .fillMaxSize()
                .background(backgroundWithAlpha)
                .pointerInput(Unit) {}
        )

        // Display the Lottie animation
        val compositionResult =
            rememberLottieComposition(
                spec = LottieCompositionSpec.RawRes(R.raw.app_animation_loader)
            )
        val progress by animateLottieCompositionAsState(
            composition = compositionResult.value,
            iterations = LottieConstants.IterateForever
        )

        if (compositionResult.value != null) {
            LottieAnimation(
                composition = compositionResult.value!!,
                progress = progress,
                modifier =
                Modifier
                    .size(200.dp)
                    .align(Alignment.Center)
            )
        }
    }
}
