package com.valkotova.wishboxwithcompose.ui.fragments.signUp

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valkotova.wishboxwithcompose.R
import com.valkotova.wishboxwithcompose.domain.useCases.auth.RegistrationUseCase
import com.valkotova.wishboxwithcompose.ui.main.CommonUIEvents
import com.valkotova.wishboxwithcompose.ui.main.Destinations
import com.valkotova.wishboxwithcompose.ui.main.UIEvents
import com.valkotova.wishboxwithcompose.ui.views.EditTextState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val registrationUseCase: RegistrationUseCase
) : ViewModel(){
    private val _state : MutableStateFlow<UIEvents> = MutableStateFlow(CommonUIEvents.EmptyEvent)
    val state : StateFlow<UIEvents>
        get() = _state.asStateFlow()

    private val _email : MutableStateFlow<EditTextState> = MutableStateFlow(EditTextState.EMPTY_STATE)
    val email : StateFlow<EditTextState>
        get() = _email.asStateFlow()

    private val _password1 : MutableStateFlow<EditTextState> = MutableStateFlow(EditTextState.EMPTY_STATE)
    val password1 : StateFlow<EditTextState>
        get() = _password1.asStateFlow()

    private val _password2 : MutableStateFlow<EditTextState> = MutableStateFlow(EditTextState.EMPTY_STATE)
    val password2 : StateFlow<EditTextState>
        get() = _password2.asStateFlow()

    //Events
    fun emailChanged(newText : String) = viewModelScope.launch {
        _email.emit(EditTextState(newText, false))
    }
    fun password1Changed(newText : String) = viewModelScope.launch {
        _password1.emit(EditTextState(newText, false))
    }
    fun password2Changed(newText : String) = viewModelScope.launch {
        _password2.emit(EditTextState(newText, false))
    }
    fun SignUpOnClick(){
        checkCredential()
    }
    fun navigateSignUp() = viewModelScope.launch {
        _state.emit(CommonUIEvents.NavigateTo(Destinations.SIGN_IN))
    }

    private suspend fun login(email : String, password: String) {
        try {
            val info = registrationUseCase.execute(email, password)
            if(info.nickname.isNullOrEmpty())
                _state.emit(CommonUIEvents.NavigateTo(Destinations.CREATE_PROFILE))
            else
            //if(info.confirmed)
                _state.emit(CommonUIEvents.NavigateTo(Destinations.LISTS))
        } catch (t : Throwable){
            _state.emit(CommonUIEvents.UIErrorNetwork(t.message?:""))
        }
    }

    private fun checkCredential() = viewModelScope.launch{
        val errorList = mutableListOf<Int>()
        checkEmail(email.value.text)?.let{
            errorList.add(it)
            _email.emit(_email.value.copy(hasError = true))
        }
        checkPassword(password1.value.text)?.let{
            errorList.add(it)
            _password1.emit(_password1.value.copy(hasError = true))
        }

        checkPassword(password2.value.text)?.let{
            errorList.add(it)
            _password2.emit(_password2.value.copy(hasError = true))
        }

        if(password2.value.text != password1.value.text){
            errorList.add(R.string.error_pwd_not_match)
            _password2.emit(_password1.value.copy(hasError = true))
            _password2.emit(_password2.value.copy(hasError = true))
        }

        if(errorList.isEmpty()){
            login(email.value.text, password1.value.text)
        } else {
            _state.emit(CommonUIEvents.UIError(errorList))
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
        if(password.length < 6)
            return R.string.error_pwd_min_length
        if(password.length > 30)
            return R.string.error_pwd_max_length
        return null
    }
}