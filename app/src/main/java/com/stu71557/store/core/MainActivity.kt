package com.stu71557.store.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.stu71557.store.core.presentation.components.Navigation
import com.stu71557.store.core.presentation.components.SearchBar
import com.stu71557.store.ui.theme.StoreTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StoreTheme(dynamicColor = false) {

                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                val searchQuery = remember { mutableStateOf(TextFieldValue()) }
                val screens = listOf(Screen.Home, Screen.Cart, Screen.Profile)
                val showBottomBar =
                    navController.currentBackStackEntryAsState().value?.destination?.route in screens.map { it.route }


                Scaffold(topBar = {
                    AnimatedContent(
                        targetState = currentDestination?.route == Screen.Search.route,
                        label = ""
                    ) {
                        if (it) {
                            SearchBar(query = searchQuery.value, onQueryChange = { textField ->
                                searchQuery.value = textField
                            }, onBackClick = {
                                navController.popBackStack()
                            }, onClearClick = {
                                searchQuery.value = TextFieldValue()
                            })
                        }
                    }
                },
                    bottomBar = {
                        AnimatedVisibility(
                            visible = showBottomBar,
                            enter = slideInVertically(animationSpec = tween(400)) { it },
                            exit = slideOutVertically(animationSpec = tween(400)) { it },
                        ) {
                            NavigationBar {
                                screens.forEach { screen ->
                                    val selected =
                                        currentDestination?.hierarchy?.any { it.route == screen.route } == true

                                    NavigationBarItem(
                                        icon = {
                                            screen.icon?.let {
                                                Icon(imageVector = screen.icon, contentDescription = null)
                                            }
                                        },
                                        label = { Text(text = screen.title.toString()) },
                                        selected = selected,
                                        onClick = {
                                            navController.navigate(screen.route) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }) { paddingValues ->
                    Navigation(
                        searchQuery = searchQuery.value.text,
                        navController = navController,
                        paddingValues = paddingValues
                    )
                }
            }
        }
    }
}

