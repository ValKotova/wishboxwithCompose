@file:OptIn(ExperimentalAnimationApi::class)

package com.valkotova.wishboxwithcompose.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.animation.composable
import com.valkotova.wishboxwithcompose.ui.fragments.signIn.SignIn
import com.valkotova.wishboxwithcompose.ui.main.theme.WishboxWithComposeTheme
import com.valkotova.wishboxwithcompose.ui.fragments.MainLists.MainLists
import com.valkotova.wishboxwithcompose.ui.fragments.browseWish.BrowseWish
import com.valkotova.wishboxwithcompose.ui.fragments.signUp.SignUp
import com.valkotova.wishboxwithcompose.ui.views.BottomBar
import com.valkotova.wishboxwithcompose.ui.views.ErrorBox
import dagger.hilt.android.AndroidEntryPoint

val springSpec = spring<IntOffset>(dampingRatio = Spring.DampingRatioMediumBouncy)
val tweenSpec = tween<IntOffset>(durationMillis = 2000, easing = CubicBezierEasing(0.08f,0.93f,0.68f,1.27f))

@ExperimentalFoundationApi
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
                                navigateToRoute = appState::navigate
                            )
                        }
                    }
                )
                {
                    AnimatedNavHost(
                        navController = appState.navController,
                        startDestination = MainDestinations.SIGN_IN,
                        enterTransition = {
                            slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = springSpec)
                        },
                        exitTransition = {
                            slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = springSpec)
                        },
                        popEnterTransition = {
                            slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = springSpec)
                        },
                        popExitTransition = {
                            slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = springSpec)
                        },
                        modifier = Modifier.padding(it)
                    ) {
                        composable(MainDestinations.SIGN_IN) {
                            SignIn(appState)
                        }
                        composable(MainDestinations.SIGN_UP) {
                            SignUp(appState)
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
                        composable("${MainDestinations.BROWSE_WISH}{${NavigationArguments.WISH_ID}}") {
                            BrowseWish(appState)
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
    navController: NavHostController = rememberAnimatedNavController()
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