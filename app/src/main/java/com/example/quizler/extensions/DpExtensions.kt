package com.example.quizler.extensions

import android.util.TypedValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun Dp.toSp(): TextUnit {
    return (toPx() / LocalContext.current.resources.displayMetrics.scaledDensity).sp
}

@Composable
fun Dp.toPx(): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        value,
        LocalContext.current.resources.displayMetrics
    )
}
