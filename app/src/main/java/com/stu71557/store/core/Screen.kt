package com.stu71557.store.core

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Apps
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String?,
    val icon: ImageVector?
) {
    data object Profile : Screen("profile", "Profile", Icons.Outlined.Person)
    data object Auth : Screen("auth", "Auth", null)
    data object Signup : Screen("signup", "Signup", null)
    data object Home : Screen("home",  "Home", Icons.Outlined.Apps)
    data object ProductDetails : Screen("product_details", "", null)
    data object Cart : Screen("cart", "My Cart", Icons.Outlined.ShoppingCart)
    data object Search : Screen("search", "Search", Icons.Outlined.Search)
}