package com.valkotova.wishboxwithcompose.ui.mvvm.MainLists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valkotova.wishboxwithcompose.domain.model.wishes.WishShortInfo
import com.valkotova.wishboxwithcompose.domain.useCases.wishes.GetMyWishesUseCase
import com.valkotova.wishboxwithcompose.ui.main.CommonUIEvents
import com.valkotova.wishboxwithcompose.ui.main.UIEvents
import com.valkotova.wishboxwithcompose.ui.views.EditTextState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainListsViewModel @Inject constructor(
    private val getMyWishesUseCase: GetMyWishesUseCase
) : ViewModel() {

    private val _wishes : MutableStateFlow<List<WishShortInfo>> = MutableStateFlow(listOf())
    val wishes : StateFlow<List<WishShortInfo>>
        get() = _wishes.asStateFlow()

    private val _state : MutableStateFlow<UIEvents> = MutableStateFlow(CommonUIEvents.EmptyEvent)
    val state : StateFlow<UIEvents>
        get() = _state.asStateFlow()

    fun onStart() {
        viewModelScope.launch {
            try {
                _wishes.emit(getMyWishesUseCase.execute(1, ""))
            }catch (t : Throwable) {
                _state.emit(CommonUIEvents.UIErrorNetwork(t.message?:""))
            }
        }
    }
}