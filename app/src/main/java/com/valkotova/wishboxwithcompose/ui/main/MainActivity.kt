package com.valkotova.wishboxwithcompose.ui.main

import android.os.Bundle
import android.util.Config.PROFILE
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.valkotova.wishboxwithcompose.ui.mvvm.signIn.SignIn
import com.valkotova.wishboxwithcompose.ui.main.theme.WishboxWithComposeTheme
import com.valkotova.wishboxwithcompose.ui.mvvm.MainLists.MainLists
import com.valkotova.wishboxwithcompose.ui.views.BottomBar
import com.valkotova.wishboxwithcompose.ui.views.ErrorBox
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WishboxWithComposeTheme {
                val viewModel : MainActivityViewModel by viewModels()
                val appState = rememberAppState(viewModel)
                val errorState = viewModel.errorState.collectAsState()
                Scaffold(
                    bottomBar = {
                        if (appState.shouldShowBottomBar) {
                            BottomBar(
                                tabs = appState.bottomBarTabs,
                                currentRoute = appState.currentRoute!!,
                                navigateToRoute = appState::navigateToBottomBarRoute
                            )
                        }
                    }
                )
                {
                    NavHost(
                        navController = appState.navController,
                        startDestination = MainDestinations.SIGN_IN,
                        modifier = Modifier.padding(it)
                    ) {
                        composable(MainDestinations.SIGN_IN) {
                            SignIn(appState)
                        }
                        composable(MainDestinations.LISTS) {
                            MainLists(appState)
                        }
                        composable(MainDestinations.CALENDAR) {
                            SignIn(appState)
                        }
                        composable(MainDestinations.FRIENDS) {
                            SignIn(appState)
                        }
                        composable(MainDestinations.MENU) {
                            SignIn(appState)
                        }
                    }
                }
                ErrorBox(errorState)
            }
        }
    }
}

@Composable
fun rememberAppState(
    mainActivityViewModel: MainActivityViewModel,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController()
) =
    remember(scaffoldState, navController) {
        AppState(scaffoldState, navController, mainActivityViewModel)
    }

//fun NavGraphBuilder.navGraph() {
//    navigation(
//        route = MainDestinations.SIGN_IN,
//        startDestination = MainDestinations.SIGN_IN
//    ) {
//        addHomeGraph()
//    }
//}

//fun NavGraphBuilder.addHomeGraph(
//    modifier: Modifier = Modifier
//) {
////    composable(HomeSections.LISTS.route) {
////        CatalogScreen()
////    }
////    composable(HomeSections.CALENDAR.route) {
////        ProfileScreen()
////    }
////    composable(HomeSections.SEARCH.route) {
////        SearchScreen()
////    }
//    composable(MainDestinations.SIGN_IN) {
//        SignIn()
//    }
//    composable(HomeSections.LISTS.route) {
//        SignIn()
//    }
//    composable(HomeSections.CALENDAR.route) {
//        SignIn()
//    }
//    composable(HomeSections.FRIENDS.route) {
//        SignIn()
//    }
//    composable(HomeSections.MENU.route) {
//        SignIn()
//    }
//}