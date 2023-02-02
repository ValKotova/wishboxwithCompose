package com.valkotova.wishboxwithcompose.ui.main

import androidx.annotation.StringRes
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(): ViewModel(){
    private val _errorState : MutableStateFlow<String> = MutableStateFlow("")
    val errorState : StateFlow<String>
    get() = _errorState.asStateFlow()

    fun showError( error : String) = viewModelScope.launch {
        _errorState.emit(error)
        async {
            delay(3000)
            _errorState.emit("")
        }
    }

    fun hideError() = viewModelScope.launch {
        _errorState.emit("")
    }
}