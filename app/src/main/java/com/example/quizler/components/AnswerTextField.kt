package com.example.quizler.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.domain.model.AnswerType
import com.example.quizler.R
import com.example.quizler.components.quiz.AnswerCharComponent
import com.example.quizler.theme.QuizlerTheme
import com.example.quizler.theme.alertRed
import com.example.quizler.theme.colorAcomodatedToLightOrDarkMode
import com.example.quizler.theme.spaceS
import com.example.quizler.theme.successGreen

@Composable
fun AnswerTextField(
    modifier: Modifier = Modifier,
    type: AnswerType,
    text: String,
    isCorrect: Boolean = false,
    onTextChange: (AnswerType, String) -> Unit = { _, _ -> },
) {
    val color = when {
        isCorrect -> successGreen()
        else -> alertRed()
    }

    var textFieldHeight by remember { mutableStateOf(Dp.Unspecified) }
    val focusManager = LocalFocusManager.current
    val buttonColor = colorAcomodatedToLightOrDarkMode(color = color)
    val localDensity = LocalDensity.current

    ElevatedCard(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            contentColor = contentColorFor(buttonColor),
            containerColor = buttonColor,
            disabledContentColor = contentColorFor(buttonColor),
            disabledContainerColor = buttonColor,
        )
    ) {
        Row(
            modifier = Modifier
                .padding(spaceS)
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnswerCharComponent(
                modifier = Modifier
                    .height(textFieldHeight)
                    .width(30.dp),
                char = type.char
            )
            Spacer(modifier = Modifier.size(spaceS))
            OutlinedTextField(
                modifier = Modifier
                    .onGloballyPositioned { layoutCoordinates ->
                        textFieldHeight = with(localDensity) { layoutCoordinates.size.height.toDp() }
                    }
                    .weight(1f),
                shape = MaterialTheme.shapes.medium,
                value = text,
                onValueChange = { onTextChange(type, it) },
                maxLines = 3,
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.answer, arrayListOf(type.char))
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(.5f),
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                )
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewAnswerTextField() {
    QuizlerTheme {
        AnswerTextField(
            type = AnswerType.B,
            isCorrect = false,
            text = ""
        )
    }
}
