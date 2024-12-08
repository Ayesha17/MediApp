package com.ayesha.mediapp.ui.common

import android.view.Gravity
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.ayesha.mediapp.R
import com.ayesha.mediapp.ui.theme.MediAppTheme

enum class ToastType( val color: Int, val icon: Int) {
    SUCCESS(R.color.fillStatusActiveLight, R.drawable.ic_filled_succes),
    ERROR(R.color.fillErrorDefaultDark, R.drawable.ic_circle_error),
    INFO(R.color.white, R.drawable.ic_circle_info),
    WARNING(R.color.fillNotificationWarningDark, R.drawable.ic_circle_warning)
}

/**
 * show the toast on top of the screens
 *
 * @param toastType are the [ToastType]
 * @param message text should be shown in toast
 * @param duration how long it will show
 * @param padding padding of toast
 * @param contentAlignment where it will appear on screen
 */

@Composable
fun BaseToast(
    toastType: ToastType = ToastType.SUCCESS,
    message: String = stringResource(id = R.string.app_name),
    duration: Int = Toast.LENGTH_LONG,
    padding: PaddingValues = PaddingValues(0.dp),
    contentAlignment: Alignment = Alignment.TopCenter
) {
    val context = LocalContext.current
    val toast = Toast(context)
    val views = ComposeView(context)
    views.setContent {
        MediAppTheme {
            SetView(
                messageTxt = message,
                resourceIcon = toastType.icon,
                borderColor = colorResource(id = toastType.color),
                backgroundColor = colorResource(id = toastType.color) ,
                padding = padding,
                contentAlignment = contentAlignment
            )
        }
    }

    views.setViewTreeLifecycleOwner(LocalLifecycleOwner.current)
    views.setViewTreeViewModelStoreOwner(LocalViewModelStoreOwner.current)
    views.setViewTreeSavedStateRegistryOwner(LocalSavedStateRegistryOwner.current)

    toast.duration = duration
    toast.view = views
    toast.setGravity(Gravity.FILL_HORIZONTAL, 0, 0)
    toast.show()
}

@Composable
fun SetView(
    messageTxt: String,
    resourceIcon: Int,
    backgroundColor: Color,
    padding: PaddingValues,
    contentAlignment: Alignment,
    borderColor: Color
) {
    var isVisible by remember { mutableStateOf(false) }

    val fadeInAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 200),
        label = ""
    )

    LaunchedEffect(key1 = true) {
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeInAnimation(fadeInAlpha),
        exit = fadeOutAnimation()
    ) {
        Box(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 16.dp),
            contentAlignment = contentAlignment
        ) {
            Surface(
                modifier =
                    Modifier
                        .wrapContentSize(),
                color = Color.Transparent
            ) {
                Row(
                    modifier =
                    Modifier
                        .border(
                            border = BorderStroke(width = 1.dp, color = borderColor),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .background(
                            color = backgroundColor,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .fillMaxWidth()
                        .height(48.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier =
                        Modifier
                            .padding(start = 16.dp, 0.dp, 0.dp, 0.dp)
                            .width(16.dp)
                            .height(16.dp),
                        painter = painterResource(id = resourceIcon),
                        contentDescription = ""
                    )
                    Text(
                        text = messageTxt,
                        color = Color.White,
                        modifier = Modifier.padding(8.dp, 0.dp, 16.dp, 0.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ShowBaseToast() {
    MediAppTheme {
        BaseToast()
    }
}

private fun fadeInAnimation(alpha: Float): EnterTransition =
    fadeIn(
        animationSpec = tween(durationMillis = 2000)
    ) +
        fadeIn(
            initialAlpha = alpha,
            animationSpec = tween(durationMillis = 2000)
        )

private fun fadeOutAnimation(): ExitTransition =
    fadeOut(
        animationSpec = tween(durationMillis = 2000)
    )
