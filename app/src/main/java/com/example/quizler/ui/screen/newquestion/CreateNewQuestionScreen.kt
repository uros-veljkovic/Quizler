package com.example.quizler.ui.screen.newquestion

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.quizler.R
import com.example.quizler.components.AnswerTextField
import com.example.quizler.components.CorrectAnswerPicker
import com.example.quizler.components.HorizontalChipPicker
import com.example.quizler.components.InfoBanner
import com.example.quizler.theme.QuizlerTheme
import com.example.quizler.theme.spaceM
import com.example.quizler.theme.spaceS
import com.example.quizler.ui.screen.home.plus

@Composable
fun CreateNewQuestionScreen(
    viewModel: CreateNewQuestionViewModel,
) {
    val state by viewModel.screenState.collectAsStateWithLifecycle()
    val infoBannerData by viewModel.infoBanner.collectAsState(initial = null)
    val scrollState = rememberScrollState()
    Scaffold(
        snackbarHost = {
            AnimatedVisibility(visible = infoBannerData != null) {
                infoBannerData?.let {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(start = spaceM, end = spaceM)
                    ) {
                        InfoBanner(data = it, isActionButtonVisible = false)
                    }
                }
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding + spaceS)
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(spaceM),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1.7f),
                    value = state.question.text,
                    onValueChange = viewModel::onQuestionUpdate,
                    maxLines = 3,
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                    placeholder = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(id = R.string.question),
                            textAlign = TextAlign.Center
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(.5f)
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                )
                if (state.isLoading) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }
                HorizontalChipPicker(
                    items = state.categories,
                    isDropdownExpanded = state.isCategoriesDropdownExpanded,
                    chosenItem = state.chosenCategory,
                    onItemClick = viewModel::onCategoryChosen,
                    onExpandDropdown = viewModel::onExpandDropdown
                )
                CorrectAnswerPicker(
                    modifier = Modifier.weight(1.5f),
                    correctAnswerType = state.chosenCorrectAnswer,
                    onCorrectAnswerChosen = viewModel::onCorrectAnswerChosen
                )
                state.answers.forEach { answer ->
                    AnswerTextField(
                        modifier = Modifier.weight(1.5f),
                        type = answer.type,
                        isCorrect = state.chosenCorrectAnswer == answer.type,
                        text = answer.text,
                        onTextChange = { type, text -> viewModel.onAnswerUpdate(type, text) }
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(bottom = 2.dp)
                ) {
                    Button(
                        modifier = Modifier.weight(1f),
                        enabled = state.isLoading.not(),
                        onClick = viewModel::onSaveQuestion
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(spaceS),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(painter = painterResource(id = R.drawable.ic_save), contentDescription = null)
                            Text(text = stringResource(id = R.string.save))
                        }
                    }
                    Spacer(modifier = Modifier.size(spaceS))
                    OutlinedButton(
                        modifier = Modifier.weight(1f),
                        enabled = state.isLoading.not(),
                        onClick = viewModel::onDeleteAll
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(spaceS),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(painter = painterResource(id = R.drawable.ic_delete), contentDescription = null)
                            Text(text = stringResource(id = R.string.reset_fields))
                        }
                    }
                }
            }
        }
    )
}

@Preview(showSystemUi = true, showBackground = true, device = Devices.PIXEL_3A)
@Preview(showSystemUi = true, showBackground = true, uiMode = UI_MODE_NIGHT_YES, device = Devices.PIXEL_4_XL)
@Composable
fun PreviewCreateNewQuestionScreen() {
    QuizlerTheme {
        Surface {
//            CreateNewQuestionScreen()
        }
    }
}
