package com.example.quizler.ui.components
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.quizler.R

@Composable
fun NoNetworkDialog(
    onTryAgain: () -> Unit
) {
    BasicDialog(
        icon = R.drawable.ic_no_wifi,
        title = stringResource(id = R.string.no_internet_connection),
        description = stringResource(id = R.string.no_internet_connection_description),
        positiveButtonText = stringResource(id = R.string.try_again),
        onPositiveButtonClick = onTryAgain
    )
}

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
fun PreviewNoNetworkDialog() {
    NoNetworkDialog() {}
}

@Preview
@Composable
fun PreviewExitDialog() {
    ExitDialog({}, {})
}
