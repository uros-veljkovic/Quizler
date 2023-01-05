package com.example.quizler.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import com.example.quizler.R
import com.example.quizler.ui.screen.quiz.ResultInfo
import com.example.quizler.ui.theme.QuizlerTheme
import com.example.quizler.ui.theme.spaceXL


@Composable
fun ResultInfoDialog(
    resultInfoData: ResultInfo,
    username: String,
    shouldSaveUsername: Boolean,
    onUsernameChange: (String) -> Unit,
    onSaveUsernameCheckBoxChecked: (Boolean) -> Unit,
    onGoToHomeScreenClick: () -> Unit
) {
    BasicDialog(
        icon = resultInfoData.icon,
        title = resultInfoData.title,
        description = resultInfoData.description,
        positiveButtonText = stringResource(id = R.string.go_to_home_screen),
        content = {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = spaceXL, end = spaceXL),
                constraintSet = resultInfoDialogConstraintSet
            ) {
                QuizlerInputField(
                    modifier = Modifier.layoutId(SAVE_USERNAME_TEXTFIELD_ID),
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Person, contentDescription = ""
                        )
                    },
                    label = stringResource(id = R.string.username),
                    value = username,
                    onValueChange = onUsernameChange
                )
                Checkbox(
                    modifier = Modifier.layoutId(SAVE_USERNAME_CHECKBOX_ID),
                    checked = shouldSaveUsername,
                    onCheckedChange = onSaveUsernameCheckBoxChecked
                )
                Text(
                    modifier = Modifier.layoutId(SAVE_USERNAME_LABEL_ID),
                    text = stringResource(id = R.string.save_username),
                    textAlign = TextAlign.Center
                )
            }
        },
        onPositiveButtonClick = onGoToHomeScreenClick
    )
}

@Preview
@Composable
fun PreviewFinishDialog() {
    QuizlerTheme {
        BasicDialog(
            icon = R.drawable.ic_in_love,
            title = stringResource(id = R.string.wow),
            description = stringResource(id = R.string.lorem),
            content = {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = spaceXL, end = spaceXL),
                    constraintSet = resultInfoDialogConstraintSet
                ) {
                    QuizlerInputField(
                        modifier = Modifier.layoutId(SAVE_USERNAME_TEXTFIELD_ID),
                        icon = {
                            Icon(imageVector = Icons.Outlined.Person, contentDescription = "")
                        },
                        label = stringResource(id = R.string.username),
                        value = "",
                        onValueChange = {}
                    )
                    Checkbox(
                        modifier = Modifier.layoutId(SAVE_USERNAME_CHECKBOX_ID),
                        checked = true,
                        onCheckedChange = {}
                    )
                    Text(
                        modifier = Modifier.layoutId(SAVE_USERNAME_LABEL_ID),
                        text = stringResource(id = R.string.save_username),
                        textAlign = TextAlign.Center
                    )
                }
            },
            positiveButtonText = stringResource(id = R.string.go_to_home_screen),
            onPositiveButtonClick = {},
        )
    }
}

private const val SAVE_USERNAME_TEXTFIELD_ID = "username"
private const val SAVE_USERNAME_CAPTION_ID = "caption"
private const val SAVE_USERNAME_CHECKBOX_ID = "checkbox"

private const val SAVE_USERNAME_LABEL_ID = "label"

private val resultInfoDialogConstraintSet = ConstraintSet {
    val textField = createRefFor(SAVE_USERNAME_TEXTFIELD_ID)
    val textFieldCaption = createRefFor(SAVE_USERNAME_CAPTION_ID)
    val checkBox = createRefFor(SAVE_USERNAME_CHECKBOX_ID)
    val label = createRefFor(SAVE_USERNAME_LABEL_ID)

    constrain(textField) {
        centerHorizontallyTo(parent)
        top.linkTo(parent.top)
    }

    constrain(textFieldCaption) {
        start.linkTo(textField.start)
        top.linkTo(textField.bottom)
    }

    constrain(checkBox) {
        start.linkTo(textFieldCaption.start)
        top.linkTo(textFieldCaption.bottom)
        bottom.linkTo(parent.bottom)
    }

    constrain(label) {
        centerVerticallyTo(checkBox)
        centerHorizontallyTo(textField)
    }
}