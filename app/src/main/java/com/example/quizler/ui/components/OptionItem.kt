package com.example.quizler.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.quizler.ui.model.IChoosableOptionItem
import com.example.quizler.ui.theme.QuizlerTheme

@Composable
fun OptionItem(
    modifier: Modifier = Modifier,
    item: IChoosableOptionItem,
    onChosen: (IChoosableOptionItem) -> Unit
) {
    Row(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = item.getIsChosen(),
            onCheckedChange = { onChosen(item) },
        )
        Text(modifier = Modifier.fillMaxWidth(), text = item.getTitle(), color = MaterialTheme.colorScheme.onSurface)
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewOptionItem() {
    QuizlerTheme {
        OptionItem(
            modifier = Modifier,
            object : IChoosableOptionItem {
                override fun getItemId(): String {
                    return "1"
                }

                override fun getTitle(): String {
                    return "Title"
                }

                override fun getIsChosen(): Boolean {
                    return true
                }
            },
            onChosen = { }
        )
    }
}
