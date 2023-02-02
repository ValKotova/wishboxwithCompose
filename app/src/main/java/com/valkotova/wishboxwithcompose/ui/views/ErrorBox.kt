package com.valkotova.wishboxwithcompose.ui.views

import androidx.annotation.StringRes
import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

@Composable
fun ErrorBox(state : State<String>) {

    AnimatedVisibility(
        visible = state.value.isNotEmpty(),
        enter = slideInHorizontally()
                + expandHorizontally(expandFrom = Alignment.End)
                + fadeIn(),
        exit = slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth })
                + shrinkHorizontally()
                + fadeOut()
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeightIn(min = 44.dp),
            tonalElevation = 10.dp,
            color = MaterialTheme.colorScheme.errorContainer
        ) {
            Text(
                modifier = Modifier.padding(20.dp),
                text = state.value,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onErrorContainer,
                textAlign = TextAlign.Center
            )
        }
    }
}

enum class ErrorBoxState {
    Opened,
    Hidden
}