package com.valkotova.wishboxwithcompose.ui.main

import androidx.annotation.StringRes
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valkotova.wishboxwithcompose.domain.model.users.CredentialsInfo
import com.valkotova.wishboxwithcompose.domain.useCases.auth.CheckTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    val checkTokenUseCase: CheckTokenUseCase
): ViewModel(){
    private val _errorState : MutableStateFlow<String> = MutableStateFlow("")
    val errorState : StateFlow<String>
    get() = _errorState.asStateFlow()

    private val _startPage : MutableStateFlow<StartPage?> = MutableStateFlow(null)
    val startPage : StateFlow<StartPage?>
        get() = _startPage.asStateFlow()

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

    fun checkToken() = viewModelScope.launch {
        try{
            val info = checkTokenUseCase.execute()
            info?.let{
                if(it.nickname.isNullOrEmpty()){
                    _startPage.emit(StartPage.CREATE_PROFILE)
                } else{
                    _startPage.emit(StartPage.MAIN_LISTS)
                }
            } ?: run {
                _startPage.emit(StartPage.SIGN_IN)
            }
        } catch(t : Throwable){
            showError(t.message?:"")
            _startPage.emit(StartPage.SIGN_IN)
        }
    }
}

enum class StartPage{
    SIGN_IN, CREATE_PROFILE, MAIN_LISTS
}