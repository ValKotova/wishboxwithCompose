package com.valkotova.wishboxwithcompose.ui.main

interface UIEvents

sealed class CommonUIEvents : UIEvents{
    object EmptyEvent : CommonUIEvents()
    data class UIError(val errors : List<Int>) : CommonUIEvents()
    data class UIErrorNetwork(val error : String) : CommonUIEvents()
    data class NavigateTo(val route : String) : CommonUIEvents()
}
