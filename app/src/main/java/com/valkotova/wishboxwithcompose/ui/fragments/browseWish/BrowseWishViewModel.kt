package com.valkotova.wishboxwithcompose.ui.fragments.browseWish

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valkotova.wishboxwithcompose.domain.model.wishes.WishInfo
import com.valkotova.wishboxwithcompose.domain.model.wishlists.WishListInfo
import com.valkotova.wishboxwithcompose.domain.useCases.wishLists.GetWishListByIdUseCase
import com.valkotova.wishboxwithcompose.domain.useCases.wishes.GetWishByIdUseCase
import com.valkotova.wishboxwithcompose.ui.main.CommonUIEvents
import com.valkotova.wishboxwithcompose.ui.main.NavigationArguments
import com.valkotova.wishboxwithcompose.ui.main.UIEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BrowseWishViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getWishByIdUseCase: GetWishByIdUseCase,
    private val getWishListByIdUseCase: GetWishListByIdUseCase,
) : ViewModel() {
    private val _state: MutableStateFlow<UIEvents> = MutableStateFlow(CommonUIEvents.EmptyEvent)
    val state: StateFlow<UIEvents>
        get() = _state.asStateFlow()

    private val _wishInfo : MutableStateFlow<WishInfo?> = MutableStateFlow(null)
    val wishInfo : StateFlow<WishInfo?>
        get() = _wishInfo.asStateFlow()

    val _wishListInfo : MutableStateFlow<WishListInfo?> = MutableStateFlow(null)
    val wishListInfo : StateFlow<WishListInfo?>
        get() = _wishListInfo.asStateFlow()

    init {
        val wishId : Int? =
            try {
                Integer.parseInt(savedStateHandle[NavigationArguments.WISH_ID]?:"")
            }catch(t: Throwable){
                null
            }
        wishId?.let{
            viewModelScope.launch {
                try{
                    _wishInfo.emit(getWishByIdUseCase.execute(wishId))
                } catch(t : Throwable){
                    _state.emit(CommonUIEvents.UIErrorNetwork(t.message?:""))
                }
            }
        }
    }

    fun getWishListInfo(context : Context) = viewModelScope.launch {
        _wishInfo.value?.let{ wish ->
            wish.list?.let{ list ->
                try{
                    _wishListInfo.emit(
                        getWishListByIdUseCase.execute(
                            list,
                            null,
                            true,
                            context
                        )
                    )
                } catch(t : Throwable){
                    _state.emit(CommonUIEvents.UIErrorNetwork(t.message?:""))
                }
            }
        }
    }
}