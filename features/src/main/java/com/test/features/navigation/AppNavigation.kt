package com.test.features.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.test.features.screens.camera.TakePictureScreen
import com.test.features.screens.details.ChallengeDetailScreen
import com.test.features.screens.home.HomeChallengeScreen
import com.test.features.screens.profile.ProfileScreen

const val FAB_EXPLODE_BOUNDS_KEY = "FAB_EXPLODE_BOUNDS_KEY"


internal sealed class Screen(val route: String) {
    object HomeChallengeScreen : Screen("home_challenge_screen")
    object ChallengeDetailsScreen : Screen("challenge_details_screen")
    object TakePictureScreen : Screen("take_picture_screen")
    object NotificationScreen : Screen("notification_screen")
    object ProfileScreen : Screen("profile_screen")

}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    SharedTransitionLayout {
        Scaffold(
            containerColor = Color.White,
            modifier = Modifier
                .navigationBarsPadding(),
        ) { padding ->
            NavHost(
                modifier = Modifier.padding(padding),
                navController = navController,
                startDestination = Screen.HomeChallengeScreen.route
            ) {
                //challenge home
                composable(Screen.HomeChallengeScreen.route) { HomeChallengeScreen(navController) }

                //Profile
                composable(Screen.ProfileScreen.route, enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(500)
                    )
                }, exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(500)
                    )
                }) { ProfileScreen(navController) }

                //challenge details
                composable(
                    route = Screen.ChallengeDetailsScreen.route + "/{theme}",
                    arguments = listOf(navArgument("theme") { type = NavType.StringType }),
                    enterTransition = {
                        if (initialState.destination.route == Screen.HomeChallengeScreen.route) {
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(500)
                            )
                        } else {
                            fadeIn(animationSpec = tween(500))
                        }
                    },
                    exitTransition = {
                        if (targetState.destination.route == Screen.HomeChallengeScreen.route) {
                            slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Right,
                                animationSpec = tween(500)
                            )
                        } else {
                            fadeOut(animationSpec = tween(500))
                        }
                    }
                ) { backStackEntry ->
                    val theme = backStackEntry.arguments?.getString("theme") ?: return@composable
                    ChallengeDetailScreen(
                        theme = theme,
                        navController = navController,
                        animatedVisibilityScope = this,
                    )
                }

                //TakePicture
                composable(Screen.TakePictureScreen.route) {
                    TakePictureScreen(navController, animatedVisibilityScope = this)
                }


                //notification
                composable(route = Screen.NotificationScreen.route, enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(500)
                    )
                }, exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(500)
                    )
                }) {
                    //NotificationScreen()
                }
            }
        }

    }
}