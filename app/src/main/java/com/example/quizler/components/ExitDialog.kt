package com.example.quizler.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.quizler.R

@Composable
fun ExitDialog(
    onConfirm: () -> Unit,
    onDecline: () -> Unit
) {
    BasicDialog(
        icon = R.drawable.ic_exit,
        title = stringResource(id = R.string.exit_app_title),
        description = stringResource(id = R.string.exit_app_description),
        positiveButtonText = stringResource(id = R.string.yes),
        onPositiveButtonClick = onConfirm,
        negativeButtonText = stringResource(id = R.string.no),
        onNegativeButtonClick = onDecline
    )
}

@Preview
@Composable
fun PreviewExitDialog() {
    ExitDialog({}, {})
}
