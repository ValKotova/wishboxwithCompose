package com.valkotova.wishboxwithcompose.ui.fragments.MainLists

import android.graphics.drawable.Drawable
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.valkotova.wishboxwithcompose.R
import com.valkotova.wishboxwithcompose.domain.model.wishes.WishShortInfo
import com.valkotova.wishboxwithcompose.ui.main.AppState
import com.valkotova.wishboxwithcompose.ui.main.CommonUIEvents
import com.valkotova.wishboxwithcompose.ui.main.MainDestinations
import com.valkotova.wishboxwithcompose.ui.main.getSmallUri
import com.valkotova.wishboxwithcompose.ui.main.theme.ColorTextCaption
import com.valkotova.wishboxwithcompose.ui.views.Header
import com.valkotova.wishboxwithcompose.ui.views.WishHolderVertical
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MainLists(appState : AppState,
              viewModel : MainListsViewModel = hiltViewModel()
) {
    //viewModel.onStart()
    val state = viewModel.state.collectAsState()
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
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
        ,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Header(title = stringResource(id = R.string.my_wish_lists), hasBack = false)
        Column(modifier = Modifier
            .background(color = MaterialTheme.colorScheme.onTertiary)
            .fillMaxSize()
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                style = MaterialTheme.typography.titleLarge,
                color = ColorTextCaption,
                text = stringResource(id = R.string.my_wishes)
            )
            WishesList(appState)
        }
    }
}

@Composable
fun WishesList(
    appState : AppState,
    viewModel : MainListsViewModel = hiltViewModel()){
    val wishesData = viewModel.wishes.collectAsState()
    val requestBuilderTransform =
        { item: WishShortInfo, requestBuilder: RequestBuilder<Drawable> ->
            requestBuilder.load(item.preview?.fileInfo?.getSmallUri())
        }
    val wishesListState = rememberLazyGridState()
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = wishesListState,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .padding(12.dp)
            .background(color = MaterialTheme.colorScheme.onTertiary)
    ){
        items(wishesData.value.size){ index ->
            WishHolderVertical(wishesData.value[index]) {
                appState.navigate("${MainDestinations.BROWSE_WISH}$it")
            }
        }
    }
}