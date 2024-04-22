package com.stu71557.store.core.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.stu71557.store.auth.presentation.auth_screen.AuthScreen
import com.stu71557.store.auth.presentation.auth_screen.AuthViewModel
import com.stu71557.store.auth.presentation.sign_up.SignupScreen
import com.stu71557.store.auth.presentation.sign_up.SignupViewModel
import com.stu71557.store.cart.presentation.cart_screen.CartScreen
import com.stu71557.store.cart.presentation.cart_screen.CartViewModel
import com.stu71557.store.core.Screen
import com.stu71557.store.products.presentation.home_screen.HomeScreen
import com.stu71557.store.products.presentation.home_screen.HomeViewModel
import com.stu71557.store.products.presentation.product_details.ProductDetailsScreen
import com.stu71557.store.products.presentation.product_details.ProductDetailsViewModel
import com.stu71557.store.products.presentation.seacrh.SearchScreen
import com.stu71557.store.profile.presentation.ProfileScreen
import com.stu71557.store.profile.presentation.ProfileViewModel

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    searchQuery: String,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController, startDestination = Screen.Auth.route
    ) {

        composable(
            route = Screen.Auth.route
        ) {
            val viewModel: AuthViewModel = hiltViewModel()
            val uiState by viewModel.state.collectAsState()

            AuthScreen(uiState = uiState,
                onClickLogin = { viewModel.login(it) },
                onNavigateToProducts = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToSignup = { navController.navigate(Screen.Signup.route) })
        }

        composable(
            route = Screen.Signup.route
        ){
            val viewModel: SignupViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()

            SignupScreen(
                uiState = uiState,
                onClickSignup = viewModel::signup,
                onNavigateToProducts = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                })
        }

        composable(
            route = Screen.Home.route
        ) {
            val viewModel: HomeViewModel = hiltViewModel()
            val uiState by viewModel.state.collectAsState()
            HomeScreen(uiState = uiState,
                onSelectedCategory = viewModel::getProducts,
                onNavigateToProductDetails = {
                    navController.navigate("${Screen.ProductDetails.route}/$it")
                },
                onNavigateToScreen = {
                    navController.navigate(it.route)
                },
                onNavigateToSearch = {
                    navController.navigate(Screen.Search.route)
                })
        }

        composable(
            route = "${Screen.ProductDetails.route}/{productId}",
            arguments = listOf(navArgument("productId") {
                type = NavType.IntType
                defaultValue = 0
            })
        ) {
            val viewModel: ProductDetailsViewModel = hiltViewModel()
            val uiState by viewModel.state.collectAsState()

            ProductDetailsScreen(
                uiState = uiState, onNavigateToCart = {
                    navController.navigate(Screen.Cart.route)
                }, onClickAddToCart = viewModel::addProductToCart
            )
        }

        composable(
            route = Screen.Cart.route
        ) {
            val viewModel: CartViewModel = hiltViewModel()
            val uiState by viewModel.state.collectAsState()

            CartScreen(
                paddingValues = paddingValues,
                uiState = uiState,
                onRemoveProductClick = viewModel::removeProduct,
                onNavigateToProductDetails = {
                    navController.navigate("${Screen.ProductDetails.route}/$it")
                })

        }
        composable(
            route = Screen.Profile.route
        ) {

            val viewModel: ProfileViewModel = hiltViewModel()
            val uiState by viewModel.state.collectAsState()

            ProfileScreen(
                uiState = uiState,
                onSaveUser = viewModel::saveUser
            )
        }
        composable(
            route = Screen.Search.route
        ) {
            val viewModel: HomeViewModel = hiltViewModel()

            LaunchedEffect(key1 = searchQuery) {
                viewModel.onSearchTextChange(searchQuery)
            }
            val searchedProducts by viewModel.searchedProducts.collectAsState()

            SearchScreen(
                searchedProducts = searchedProducts,
                paddingValues = paddingValues,
                onNavigateToProductDetails = {
                navController.navigate("${Screen.ProductDetails.route}/$it")
            })
        }
    }

}