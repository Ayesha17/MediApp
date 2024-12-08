package com.ayesha.mediapp.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp


/**
 * Modal bottom sheets are used as an alternative to inline menus or simple dialogs on mobile,
 * especially when offering a long list of action items, or when items require longer descriptions
 * and icons. Like dialogs, modal bottom sheets appear in front of app content, disabling all other
 * app functionality when they appear, and remaining on screen until confirmed, dismissed, or a
 * required action has been taken.
 *
 * @param modalBottomSheetState The state of the bottom sheet.
 * @param dragHandle Optional visual marker to swipe the bottom sheet.
 * @param content  The content to be displayed inside the bottom sheet.
 * @param sheetShape The shape of the bottom sheet.
 * @param sheetElevation The tonal elevation of this bottom sheet.
 * @param sheetBackgroundColor The color used for the background of this bottom sheet
 * @param sheetContentColor The preferred color for content inside this bottom sheet
 * @param scrimColor Color of the scrim that obscures content when the bottom sheet is open.
 * @param onDismiss Executes when the user clicks outside of the bottom sheet, after sheet
 * animates to [Hidden].
 */
@OptIn(ExperimentalMaterial3Api::class )
@Composable
@SuppressWarnings("kotlin:S107")
fun BaseGenericBottomSheet(
    modalBottomSheetState: SheetState = rememberModalBottomSheetState(),
    dragHandle: Boolean = false,
    content: @Composable () -> Unit,
    sheetShape: Shape =BottomSheetDefaults.ExpandedShape,
    sheetElevation: Dp =BottomSheetDefaults.Elevation,
    sheetBackgroundColor: Color = Color.White,
    sheetContentColor: Color = contentColorFor(sheetBackgroundColor),
    scrimColor: Color = BottomSheetDefaults.ScrimColor,
    onDismiss: () -> Unit = {}
) {
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        shape = sheetShape,
        scrimColor = scrimColor,
        containerColor = sheetBackgroundColor,
        contentColor = sheetContentColor,
        tonalElevation = sheetElevation,
        dragHandle = { if (dragHandle) BottomSheetDefaults.DragHandle() }
    ) {
        Column(modifier = Modifier.navigationBarsPadding()) {
            content()
        }
    }
}
