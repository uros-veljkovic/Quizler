package com.example.quizler.components.info

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.quizler.theme.spaceL
import com.example.quizler.theme.spaceM
import com.example.quizler.theme.spaceXL

@Composable
fun InfoScreen(
    modifier: Modifier = Modifier,
    variant: InfoScreenVariants,
    onDone: (() -> Unit)? = null
) {

    Column(
        modifier = modifier
            .padding(start = spaceXL, end = spaceXL)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            modifier = Modifier.size(150.dp),
            painter = painterResource(id = variant.iconResId),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.size(width = 0.dp, height = spaceM))
        Text(
            text = stringResource(id = variant.titleResId),
            style = MaterialTheme.typography.headlineSmall,
        )
        Spacer(modifier = Modifier.size(width = 0.dp, height = spaceL))
        Text(
            text = stringResource(id = variant.descriptionResId),
            textAlign = TextAlign.Center,
        )
        variant.actionButton?.let {
            Spacer(modifier = Modifier.size(spaceXL))
            Button(onClick = { onDone?.invoke() }) {
                Text(text = stringResource(id = it))
            }
        }
    }
}
