package com.valkotova.wishboxwithcompose.ui.fragments.createProfile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valkotova.wishboxwithcompose.domain.model.users.Gender
import com.valkotova.wishboxwithcompose.domain.useCases.upload.UploadAvatarUseCase
import com.valkotova.wishboxwithcompose.domain.useCases.user.CreateProfileUseCase
import com.valkotova.wishboxwithcompose.ui.main.CommonUIEvents
import com.valkotova.wishboxwithcompose.ui.main.Destinations
import com.valkotova.wishboxwithcompose.ui.main.UIEvents
import com.valkotova.wishboxwithcompose.ui.views.EditTextState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.lang.Thread.State
import java.net.URI
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class CreateProfileViewModel @Inject constructor(
    private val createProfileUseCase: CreateProfileUseCase,
    private val uploadAvatarUseCase: UploadAvatarUseCase
) : ViewModel() {
    companion object{
        private val NICK_NAME_DOT_REGEX = Pattern.compile("^(?!\\.)(?!.*\\.\$).*\$")
        private val NICK_NAME_DOTS_REGEX = Pattern.compile("^(?!.*\\.\\.).*\$")
        private val NICK_NAME_REGEX = Pattern.compile("^[a-zA-Z0-9._]+\$")
        private val NICKNAME_MAX_LENGTH = 30
        private val NICKNAME_MIN_LENGTH = 4
        private val NAME_MAX_LENGTH = 300
    }
    private val _state: MutableStateFlow<UIEvents> = MutableStateFlow(CommonUIEvents.EmptyEvent)
    val state: StateFlow<UIEvents>
        get() = _state.asStateFlow()

    private val _image: MutableStateFlow<String> = MutableStateFlow("")
    val image: StateFlow<String>
        get() = _image.asStateFlow()
    private var _imageNotUploaded = false

    private val _nickname: MutableStateFlow<EditTextState> = MutableStateFlow(EditTextState("", false))
    val nickname: StateFlow<EditTextState>
        get() = _nickname.asStateFlow()

    private val _name: MutableStateFlow<EditTextState> = MutableStateFlow(EditTextState("", false))
    val name: StateFlow<EditTextState>
        get() = _name.asStateFlow()

    private val _birthday : MutableStateFlow<Long> = MutableStateFlow(LocalDate.now().atStartOfDay(
        ZoneId.systemDefault()).toInstant().toEpochMilli())
    val birthday : StateFlow<Long>
        get() = _birthday.asStateFlow()

    private val _gender: MutableStateFlow<Gender> = MutableStateFlow(Gender.unknown)
    val gender: StateFlow<Gender>
        get() = _gender.asStateFlow()

    fun onNickNameChange(text : String) = viewModelScope.launch {
        _nickname.emit(EditTextState(text, false))
    }

    fun onNameChange(text : String) = viewModelScope.launch {
        _name.emit(EditTextState(text, false))
    }

    fun setImage(uri : String) = viewModelScope.launch {
        _image.emit(uri)
    }

    fun launchCropper(uri : Uri) = viewModelScope.launch {
        _state.emit(CreateProfileEvents.LaunchCropping(uri))
    }
    fun clearState() = viewModelScope.launch {
        _state.emit(CommonUIEvents.EmptyEvent)
    }
    fun setBirthday(date : Long) = viewModelScope.launch {
        _birthday.emit(date)
    }
    fun setGender(gender : Gender) = viewModelScope.launch {
        _gender.emit(gender)
    }
    fun saveProfile() = viewModelScope.launch {
        if(!check()){
            try {
                val mediaInfo = if (_imageNotUploaded) {
                     uploadAvatarUseCase.execute(File(_image.value))
                } else null
                createProfileUseCase.execute(
                    nickname = _nickname.value.text,
                    fullName = _name.value.text,
                    gender = _gender.value,
                    birthday = _birthday.value.let{
                        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                        formatter.format(
                            LocalDateTime.ofInstant(
                                Instant.ofEpochMilli(it),
                                TimeZone.getDefault().toZoneId()
                            ))
                    },
                    about = "",
                    social = listOf(),
                    avatar = mediaInfo?.id,

                )
                _state.emit(CommonUIEvents.NavigateTo(Destinations.LISTS))
            } catch (t : Throwable){
                _state.emit(CommonUIEvents.UIErrorNetwork(t.message?:""))
            }
        }
    }
    private suspend fun check() : Boolean {
        return checkNickname() || checkName()
    }

    private suspend fun checkName(): Boolean {
        var result = false
        if(_name.value.text.isEmpty()){
            _name.emit(_name.value.copy(hasError = true))
            result = true
        }
        if(_name.value.text.length > NAME_MAX_LENGTH){
            _name.emit(_name.value.copy(hasError = true))
            result = true
        }
        return result
    }

    private suspend fun checkNickname(): Boolean {
        var result = false
        if(_nickname.value.text.isEmpty()){
            _nickname.emit(_nickname.value.copy(hasError = true))
            result = true
        }
        if(_nickname.value.text.length < NICKNAME_MIN_LENGTH){
            _nickname.emit(_nickname.value.copy(hasError = true))
            result = true
        }
        if(_nickname.value.text.length > NICKNAME_MAX_LENGTH){
            _nickname.emit(_nickname.value.copy(hasError = true))
            result = true
        }
        if(NICK_NAME_DOT_REGEX.matcher(_nickname.value.text).matches()){
            _nickname.emit(_nickname.value.copy(hasError = true))
            result = true
        }
        if(NICK_NAME_DOTS_REGEX.matcher(_nickname.value.text).matches()){
            _nickname.emit(_nickname.value.copy(hasError = true))
            result = true
        }
        if(NICK_NAME_REGEX.matcher(_nickname.value.text).matches()){
            _nickname.emit(_nickname.value.copy(hasError = true))
            result = true
        }
        return result
    }
}
sealed class CreateProfileEvents : UIEvents {
    data class LaunchCropping(val uri : Uri) : CreateProfileEvents()
}

