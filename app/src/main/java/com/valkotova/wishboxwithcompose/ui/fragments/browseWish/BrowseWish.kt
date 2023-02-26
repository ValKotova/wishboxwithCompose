package com.valkotova.wishboxwithcompose.ui.fragments.browseWish

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.valkotova.wishboxwithcompose.R
import com.valkotova.wishboxwithcompose.ui.fragments.signIn.SignInViewModel
import com.valkotova.wishboxwithcompose.ui.main.AppState
import com.valkotova.wishboxwithcompose.ui.main.StatesManagement
import com.valkotova.wishboxwithcompose.ui.main.theme.ColorTextGeneral
import com.valkotova.wishboxwithcompose.ui.views.FieldWithHint
import com.valkotova.wishboxwithcompose.ui.views.Header
import com.valkotova.wishboxwithcompose.ui.views.ImageSlider
import java.lang.reflect.Field

@ExperimentalFoundationApi
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BrowseWish(appState : AppState,
               viewModel : BrowseWishViewModel = hiltViewModel()
){
    val scrollState = rememberScrollState()
    val state = viewModel.state.collectAsState()
    val wishInfo = viewModel.wishInfo.collectAsState()
    val wishListInfo = viewModel.wishListInfo.collectAsState()
    val context = LocalContext.current

    StatesManagement(appState = appState, state = state){

    }
    wishInfo.value?.let{ wish ->
        viewModel.getWishListInfo(context)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.Start
        ) {
            Header(title = stringResource(id = R.string.browse_wish), hasBack = true, navController = appState.navController)
            ImageSlider(wish.previews)
            Text(
                text = wish.name,
                modifier = Modifier
                    .padding(horizontal = 30.dp, vertical = 30.dp),
                style = MaterialTheme.typography.bodyLarge,
                color = ColorTextGeneral,
            )
            if(!wish.link.isNullOrBlank()) {
                FieldWithHint(
                    modifier = Modifier.padding(start = 30.dp, top = 10.dp, end = 20.dp),
                    text = wish.link, hint = stringResource(
                        id = R.string.hint_link
                    ),
                    isLink = true
                )
            }
            if(!wish.price.isNullOrBlank()) {
                FieldWithHint(modifier = Modifier.padding(start = 30.dp, top = 10.dp, end = 20.dp), text = wish.price, hint = stringResource(
                    id = R.string.price
                ))
            }
            wishListInfo.value?.let {
                FieldWithHint(modifier = Modifier.padding(start = 30.dp, top = 10.dp, end = 20.dp), text = it.name, hint = stringResource(
                    id = R.string.list
                ))    
            } ?: run {
                FieldWithHint(modifier = Modifier.padding(start = 30.dp, top = 10.dp, end = 20.dp), text = stringResource(id = R.string.no_list), hint = stringResource(
                    id = R.string.list
                ))
            }
            
        }
    }
}