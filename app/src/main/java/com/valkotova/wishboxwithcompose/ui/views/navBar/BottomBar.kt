package com.valkotova.wishboxwithcompose.ui.views

import androidx.compose.animation.core.SpringSpec
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.valkotova.wishboxwithcompose.ui.main.HomeSections
import com.valkotova.wishboxwithcompose.ui.views.navBar.BottomNavIndicator
import com.valkotova.wishboxwithcompose.ui.views.navBar.BottomNavLayout
import com.valkotova.wishboxwithcompose.ui.views.navBar.BottomNavigationItem

@Composable
fun BottomBar(
    tabs: Array<HomeSections>,
    currentRoute: String,
    navigateToRoute: (String) -> Unit,
    color: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.background
) {
    val routes = remember { tabs.map { it.route } }
    val currentSection = tabs.first { it.route == currentRoute }

    Surface(
        color = color,
        contentColor = contentColor
    ) {
        val springSpec = SpringSpec<Float>(
            stiffness = 800f,
            dampingRatio = 0.8f
        )
        BottomNavLayout(
            selectedIndex = currentSection.ordinal,
            itemCount = routes.size,
            indicator = { BottomNavIndicator() },
            animSpec = springSpec,
            modifier = Modifier.navigationBarsPadding(),
            bottomNavHeight = 40.dp,
            content = {
                tabs.forEach { section ->
                    val selected = section == currentSection
//                    val tint by animateColorAsState(
//                        if (selected) {
//                            color
//                        } else {
//                            contentColor
//                        }
//                    )

                    BottomNavigationItem(
                        icon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(section.icon),
                                tint = contentColor,
                                contentDescription = null
                            )
                        },
                        selected = selected,
                        onSelected = { navigateToRoute(section.route) },
                        animSpec = springSpec,
                    )
                }
            })
    }
}