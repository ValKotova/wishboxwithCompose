package com.valkotova.wishboxwithcompose.ui.mvvm.signIn

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.sourceInformation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valkotova.wishboxwithcompose.R
import com.valkotova.wishboxwithcompose.domain.useCases.auth.LoginUseCase
import com.valkotova.wishboxwithcompose.ui.main.CommonUIEvents
import com.valkotova.wishboxwithcompose.ui.main.MainDestinations
import com.valkotova.wishboxwithcompose.ui.main.UIEvents
import com.valkotova.wishboxwithcompose.ui.views.EditTextState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _email : MutableStateFlow<EditTextState> = MutableStateFlow(EditTextState.EMPTY_STATE)
    val email : StateFlow<EditTextState>
        get() = _email.asStateFlow()

    private val _password : MutableStateFlow<EditTextState> = MutableStateFlow(EditTextState.EMPTY_STATE)
    val password : StateFlow<EditTextState>
        get() = _password.asStateFlow()

    private val _state : MutableStateFlow<UIEvents> = MutableStateFlow(CommonUIEvents.EmptyEvent)
    val state : StateFlow<UIEvents>
        get() = _state.asStateFlow()

    fun emailChanged(newText : String) = viewModelScope.launch {
        _email.emit(EditTextState(newText, false))
    }

    fun passwordChanged(newText : String) = viewModelScope.launch {
        _password.emit(EditTextState(newText, false))
    }

    fun SignInOnClick(){
        checkCredential()
    }

    private fun checkCredential() = viewModelScope.launch{
        val errorList = mutableListOf<Int>()
        checkEmail(email.value.text)?.let{
            errorList.add(it)
            _email.emit(_email.value.copy(hasError = true))
        }
        checkPassword(password.value.text)?.let{
            errorList.add(it)
            _password.emit(_password.value.copy(hasError = true))
        }
        if(errorList.isEmpty()){
            login(email.value.text, password.value.text)
        } else {
            _state.emit(CommonUIEvents.UIError(errorList))
        }
    }

    private suspend fun login(email : String, password: String) {
        try {
            val info = loginUseCase.execute(email, password)
            if(info.confirmed)
                _state.emit(CommonUIEvents.NavigateTo(MainDestinations.LISTS))
        } catch (t : Throwable){
            _state.emit(CommonUIEvents.UIErrorNetwork(t.message?:""))
        }
    }

    private fun checkEmail(email : String) : Int? {
        if (!email.contains("@")) {
            return R.string.error_email_dog
        }
        if (email.contains(" ")) {
            return R.string.error_email_contain_space
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return R.string.error_email_invalid
        }
        return null
    }

    private fun checkPassword(password : String) : Int?{
        if(password.contains(" ")){
            return R.string.error_credentials_password_is_not_valid
        }
        if(password.isBlank()){
            return R.string.error_credentials_password_is_not_valid
        }
        return null
    }
}