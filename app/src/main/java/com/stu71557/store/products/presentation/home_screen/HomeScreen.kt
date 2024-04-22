package com.stu71557.store.products.presentation.home_screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Sort
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.stu71557.store.core.Screen
import com.stu71557.store.core.presentation.customdrawer.component.CustomDrawer
import com.stu71557.store.core.presentation.customdrawer.component.CustomDrawerState
import com.stu71557.store.core.presentation.customdrawer.component.isOpened
import com.stu71557.store.core.presentation.customdrawer.component.opposite
import com.stu71557.store.core.utils.coloredShadow
import com.stu71557.store.products.domain.model.Product
import kotlin.math.roundToInt


@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onSelectedCategory: (String) -> Unit,
    onNavigateToProductDetails: (Int) -> Unit,
    onNavigateToScreen: (Screen) -> Unit,
    onNavigateToSearch: () -> Unit,
) {
    var drawerState by remember {
        mutableStateOf(CustomDrawerState.Closed)
    }
    var selectedScreen: Screen by remember {
        mutableStateOf(Screen.Home)
    }
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current.density
    val screenWidth = remember {
        derivedStateOf { (configuration.screenWidthDp * density).roundToInt() }
    }
    val offsetValue by remember {
        derivedStateOf { (screenWidth.value / 4.5).dp }
    }
    val animateOffset by animateDpAsState(
        targetValue = if (drawerState.isOpened()) offsetValue else 0.dp, label = "Animated Offset"
    )
    val animatedScale by animateFloatAsState(
        targetValue = if (drawerState.isOpened()) 0.9f else 1f, label = "Animated Scale"
    )
    BackHandler(
        enabled = drawerState.isOpened()
    ) {
        drawerState = CustomDrawerState.Closed
    }
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.06f))
            .statusBarsPadding()
            .navigationBarsPadding()
            .fillMaxSize()
    ) {
        CustomDrawer(selectedScreen = selectedScreen, onScreenClick = {
            selectedScreen = it
            onNavigateToScreen(it)
        }, onCloseClick = { drawerState = CustomDrawerState.Closed })
        HomeContent(
            modifier = Modifier
                .offset(x = animateOffset)
                .scale(scale = animatedScale)
                .coloredShadow(
                    color = Color.Black, alpha = 0.1f, shadowRadius = 50.dp
                ),
            uiState = uiState,
            drawerState = drawerState,
            onSelectedCategory = onSelectedCategory,
            onNavigateToProductDetails = onNavigateToProductDetails,
            onDrawerClick = {
                drawerState = it
            },
            onNavigateToSearch = onNavigateToSearch
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    uiState: HomeUiState,
    drawerState: CustomDrawerState,
    onDrawerClick: (CustomDrawerState) -> Unit,
    onSelectedCategory: (String) -> Unit,
    onNavigateToProductDetails: (Int) -> Unit,
    onNavigateToSearch: () -> Unit,
    modifier: Modifier = Modifier,
) {


    Scaffold(modifier = modifier.clickable(enabled = drawerState == CustomDrawerState.Opened) {
        onDrawerClick(CustomDrawerState.Closed)
    }, topBar = {
        TopAppBar(title = {

        }, navigationIcon = {
            IconButton(onClick = { onDrawerClick(drawerState.opposite()) }) {
                Icon(imageVector = Icons.Outlined.Menu, contentDescription = null)
            }
        }, actions = {
            IconButton(onClick = onNavigateToSearch) {
                Icon(imageVector = Icons.Outlined.Search, contentDescription = null)
            }
        })
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
                .padding(horizontal = 10.dp), verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {


            Categories(
                categories = uiState.productCategories,
                categorySelected = uiState.categorySelected,
                onSelectedCategory = onSelectedCategory
            )

            AnimatedContent(targetState = uiState.isLoading, label = "") {
                if (it) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                } else {
                    ProductList(
                        products = uiState.products,
                        onClickProduct = onNavigateToProductDetails,
                        paddingValues = PaddingValues(bottom = paddingValues.calculateBottomPadding())
                    )

                }
            }

        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Categories(
    categories: List<String>,
    categorySelected: String,
    onSelectedCategory: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    FlowRow(
        modifier = modifier.horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        categories.forEach { category ->
            val isSelected = category == categorySelected
            InputChip(
                colors = InputChipDefaults.inputChipColors(
                selectedContainerColor = MaterialTheme.colorScheme.primary,
                selectedLabelColor = MaterialTheme.colorScheme.onPrimary
            ), shape = RoundedCornerShape(18.dp),
                selected = isSelected, onClick = {
                if (category != categorySelected) {
                    onSelectedCategory(category)
                }
            }, label = {
                Text(
                    text = category, style = MaterialTheme.typography.bodyMedium
                )
            })
        }
    }


}


@Composable
fun ProductList(
    products: List<Product>,
    paddingValues: PaddingValues,
    onClickProduct: (Int) -> Unit, modifier: Modifier = Modifier
) {

    LazyVerticalGrid(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = paddingValues,
        columns = GridCells.Fixed(3)
    ) {

        items(products, key = { it.id }) { product ->
            ProductItem(product = product, onClick = { onClickProduct(product.id) })
        }
    }
}

@Composable
private fun ProductItem(
    product: Product, onClick: () -> Unit
) {

    Card(
        modifier = Modifier.size(width = 150.dp, height = 190.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
        )
    ) {
        Column(
            modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                model = product.image,
                contentScale = ContentScale.Fit,
                contentDescription = null
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = product.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = "$" + product.price,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )

        }
    }
}