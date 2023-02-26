package com.valkotova.wishboxwithcompose.ui.fragments.signUp

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.valkotova.wishboxwithcompose.R
import com.valkotova.wishboxwithcompose.ui.main.AppState
import com.valkotova.wishboxwithcompose.ui.main.StatesManagement
import com.valkotova.wishboxwithcompose.ui.views.Header
import com.valkotova.wishboxwithcompose.ui.views.WhiteEditText
import com.valkotova.wishboxwithcompose.ui.views.buttons.BlueButton

@Composable
fun SignUp(appState : AppState,
           viewModel : SignUpViewModel = hiltViewModel()
){
    val scrollState = rememberScrollState()
    val state = viewModel.state.collectAsState()
    StatesManagement(appState = appState, state = state){
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
        ,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Header(title = stringResource(id = R.string.sign_up), hasBack = false)
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(200.dp, 200.dp)
        )
        WhiteEditText(
            hint = stringResource(id = R.string.email),
            modifier = Modifier
                .padding(horizontal = 30.dp, vertical = 12.dp)
                .fillMaxWidth(),
            state = viewModel.email.collectAsState(),
            onValueChange = {
                viewModel.emailChanged(it)
            }
        )
        WhiteEditText(
            hint = stringResource(id = R.string.hint_password1),
            modifier = Modifier
                .padding(horizontal = 30.dp, vertical = 12.dp)
                .fillMaxWidth(),
            state = viewModel.password1.collectAsState(),
            onValueChange = {
                viewModel.password1Changed(it)
            }
        )
        WhiteEditText(
            hint = stringResource(id = R.string.hint_password2),
            modifier = Modifier
                .padding(horizontal = 30.dp, vertical = 12.dp)
                .fillMaxWidth(),
            state = viewModel.password2.collectAsState(),
            onValueChange = {
                viewModel.password2Changed(it)
            }
        )
        BlueButton(
            text = stringResource(id = R.string.sign_up),
            modifier = Modifier
                .padding(horizontal = 30.dp, vertical = 12.dp)
                .fillMaxWidth(),
            onClick = {
                //appState.navigateToBottomBarRoute(HomeSections.LISTS.route)
                viewModel.SignUpOnClick()
            }
        )
        Text(
            text = stringResource(id = R.string.forgot_pass),
            modifier = Modifier
                .clickable {
                    viewModel.navigateSignUp()
                }
                .padding(horizontal = 30.dp, vertical = 30.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            textDecoration = TextDecoration.Underline
        )
    }
}