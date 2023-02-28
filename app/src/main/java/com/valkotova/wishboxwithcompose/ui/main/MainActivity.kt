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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.valkotova.wishboxwithcompose.ui.fragments.MainLists.MainLists
import com.valkotova.wishboxwithcompose.ui.fragments.browseWish.BrowseWish
import com.valkotova.wishboxwithcompose.ui.fragments.createProfile.CreateProfile
import com.valkotova.wishboxwithcompose.ui.fragments.signIn.SignIn
import com.valkotova.wishboxwithcompose.ui.fragments.signUp.SignUp
import com.valkotova.wishboxwithcompose.ui.main.theme.WishboxWithComposeTheme
import com.valkotova.wishboxwithcompose.ui.views.BottomBar
import com.valkotova.wishboxwithcompose.ui.views.ErrorBox
import dagger.hilt.android.AndroidEntryPoint

val springSpec = spring<IntOffset>(dampingRatio = Spring.DampingRatioMediumBouncy)
val tweenSpec =
    tween<IntOffset>(durationMillis = 2000, easing = CubicBezierEasing(0.08f, 0.93f, 0.68f, 1.27f))

@ExperimentalFoundationApi
@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WishboxWithComposeTheme {
                val viewModel: MainActivityViewModel by viewModels()
                viewModel.checkToken()
                val appState = rememberAppState(viewModel)
                val errorState = viewModel.errorState.collectAsState()
                val startPage = viewModel.startPage.collectAsState()
                Scaffold(bottomBar = {
                    if (appState.shouldShowBottomBar) {
                        BottomBar(
                            tabs = appState.bottomBarTabs,
                            currentRoute = appState.currentRoute!!,
                            navigateToRoute = appState::navigate
                        )
                    }
                }) { paddingValues ->
                    startPage.value?.let { page ->
                        AnimatedNavHost(
                            navController = appState.navController,
                            startDestination = when (page) {
                                StartPage.SIGN_IN -> Destinations.SIGN_IN
                                StartPage.CREATE_PROFILE -> Destinations.CREATE_PROFILE
                                StartPage.MAIN_LISTS -> Destinations.LISTS
                            },
                            enterTransition = {
                                slideInHorizontally(
                                    initialOffsetX = { 1000 }, animationSpec = springSpec
                                )
                            },
                            exitTransition = {
                                slideOutHorizontally(
                                    targetOffsetX = { -1000 }, animationSpec = springSpec
                                )
                            },
                            popEnterTransition = {
                                slideInHorizontally(
                                    initialOffsetX = { -1000 }, animationSpec = springSpec
                                )
                            },
                            popExitTransition = {
                                slideOutHorizontally(
                                    targetOffsetX = { 1000 }, animationSpec = springSpec
                                )
                            },
                            modifier = Modifier.padding(paddingValues)
                        ) {
                            composable(Destinations.SIGN_IN) {
                                SignIn(appState)
                            }
                            composable(Destinations.SIGN_UP) {
                                SignUp(appState)
                            }
                            composable(Destinations.LISTS) {
                                MainLists(appState)
                            }
                            composable(Destinations.CALENDAR) {
                                SignIn(appState)
                            }
                            composable(Destinations.FRIENDS) {
                                SignIn(appState)
                            }
                            composable(Destinations.MENU) {
                                SignIn(appState)
                            }
                            composable("${Destinations.BROWSE_WISH}{${NavigationArguments.WISH_ID}}") {
                                BrowseWish(appState)
                            }
                            composable(Destinations.CREATE_PROFILE) {
                                CreateProfile(appState)
                            }
                            //                        composable("${Destinations.CROP_IMAGE}{${NavigationArguments.IMAGE_URI}") { backStackEntry ->
                            //                        CropFragment(appState, backStackEntry.arguments?.getString(NavigationArguments.IMAGE_URI))
                            //                        }
                        }
                    }
                }
                ErrorBox(errorState)
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun rememberAppState(
    mainActivityViewModel: MainActivityViewModel,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberAnimatedNavController()
) = remember(scaffoldState, navController) {
    AppState(scaffoldState, navController, mainActivityViewModel)
}