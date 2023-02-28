package com.valkotova.wishboxwithcompose.ui.main

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.res.stringResource

@Composable
fun StatesManagement(
    appState : AppState,
    state : State<UIEvents>,
    customStatesCheck : @Composable (state : State<UIEvents>) -> Unit){
    when(state.value){
        is CommonUIEvents.UIError -> {
            val result = java.lang.StringBuilder()
            for(@StringRes id in (state.value as CommonUIEvents.UIError).errors){
                result.append(stringResource(id))
                result.append('\n')
            }
            result.delete(result.lastIndex, result.lastIndex)
            appState.mainViewModel.showError(result.toString())
        }
        is CommonUIEvents.UIErrorNetwork -> {
            appState.mainViewModel.showError((state.value as CommonUIEvents.UIErrorNetwork).error)
        }
        is CommonUIEvents.NavigateTo -> {
            (state.value as? CommonUIEvents.NavigateTo)?.route?.let{
                appState.navigate(it)
            }
        }
        else -> {
            customStatesCheck.invoke(state)
        }
    }
}