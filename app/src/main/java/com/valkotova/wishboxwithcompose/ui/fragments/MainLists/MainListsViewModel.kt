package com.valkotova.wishboxwithcompose.ui.fragments.MainLists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valkotova.wishboxwithcompose.domain.model.wishes.WishShortInfo
import com.valkotova.wishboxwithcompose.domain.useCases.wishes.GetMyWishesUseCase
import com.valkotova.wishboxwithcompose.ui.main.CommonUIEvents
import com.valkotova.wishboxwithcompose.ui.main.UIEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
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

    init{
        onStart()
    }
    private fun onStart() {
        viewModelScope.async {
            try {
                _wishes.emit(getMyWishesUseCase.execute(1, ""))
            }catch (t : Throwable) {
                _state.emit(CommonUIEvents.UIErrorNetwork(t.message?:""))
            }
        }.start()
    }
}