package com.example.quizler.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.quizler.domain.model.QuizMode
import com.example.quizler.ui.theme.spaceM
import com.example.quizler.ui.theme.spaceS

@Composable
fun QuizModes(
    cardTitle: String,
    cardDescription: String,
    state: List<QuizMode>,
    onModeSelected: (QuizMode) -> Unit
) {
    AnimatedVisibility(visible = state.isNotEmpty()) {
        Card {
            Column(
                modifier = Modifier.padding(spaceM),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = cardTitle, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.padding(bottom = spaceS))
                Disclaimer(
                    dividerWidth = 2.dp, text = cardDescription
                )
                Spacer(modifier = Modifier.padding(bottom = spaceM))
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    state = rememberLazyListState(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    items(items = state, key = { it.id }) { mode ->
                        QuizModeCard(mode = mode, onModeSelected = onModeSelected)
                        Spacer(modifier = Modifier.size(spaceS))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.size(spaceM))
    }
}
