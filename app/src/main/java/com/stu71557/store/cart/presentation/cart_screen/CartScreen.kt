package com.stu71557.store.cart.presentation.cart_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.RestoreFromTrash
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.stu71557.store.cart.domain.model.ProductSimple

@Composable
fun CartScreen(
    paddingValues: PaddingValues,
    uiState: CartUiState,
    onRemoveProductClick: (Int) -> Unit,
    onNavigateToProductDetails: (Int) -> Unit,
) {


    Column(
        modifier = Modifier.padding(10.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    Text(
                        text = "${uiState.products.size} Item",
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    )
                }

                items(uiState.products) { product ->
                    CartItem(
                        productSimple = product,
                        onClick = { onNavigateToProductDetails(product.id) },
                        onRemoveProductClick = onRemoveProductClick,
                    )
                }
            }

        }

        ConstraintLayout(
            modifier = Modifier
                .padding(10.dp)
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            val (progressIndicator, textSubtotal, textSubtotalValue, textDelivery, textDeliveryValue, textTotalCost, textTotalCostValue, buttonCheckout) = createRefs()

            if (uiState.isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(progressIndicator) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        })
            }

            Text(
                modifier = Modifier.constrainAs(textSubtotal) {
                    val anchor = if (uiState.isLoading) progressIndicator.bottom else parent.top
                    top.linkTo(anchor, margin = 10.dp)
                    start.linkTo(parent.start)
                },
                text = "Subtotal",
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
            )
            Text(
                modifier = Modifier.constrainAs(textDelivery) {
                    top.linkTo(textSubtotal.bottom, margin = 5.dp)
                    start.linkTo(parent.start)
                },
                text = "Delivery",
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
            )
            Text(
                modifier = Modifier.constrainAs(textSubtotalValue) {
                    top.linkTo(textSubtotalValue.top)
                    end.linkTo(parent.end)
                },

                text = "$" + uiState.subtotal, fontWeight = FontWeight.Medium, fontSize = 16.sp
            )
            Text(
                modifier = Modifier.constrainAs(textDeliveryValue) {
                    top.linkTo(textSubtotalValue.bottom, margin = 5.dp)
                    end.linkTo(parent.end)
                }, text = "$0.0", fontWeight = FontWeight.Medium, fontSize = 16.sp
            )
            Text(
                modifier = Modifier.constrainAs(textTotalCost) {
                    top.linkTo(textDelivery.bottom, margin = 10.dp)
                    start.linkTo(parent.start)
                }, text = "Total Cost", fontWeight = FontWeight.Medium, fontSize = 16.sp
            )
            Text(
                modifier = Modifier.constrainAs(textTotalCostValue) {
                    top.linkTo(textDeliveryValue.bottom, margin = 10.dp)
                    end.linkTo(parent.end)
                },
                text = "$" + uiState.subtotal,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary
            )

            Button(modifier = Modifier
                .constrainAs(buttonCheckout) {
                    top.linkTo(textTotalCost.bottom, margin = 10.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .height(50.dp)
                .fillMaxWidth(), colors = ButtonDefaults.buttonColors(
            ), shape = RoundedCornerShape(14.dp), onClick = {}) {

                Text(text = "Checkout")
            }
        }
    }


}

@Composable
fun CartItem(
    productSimple: ProductSimple,
    onClick: () -> Unit,
    onRemoveProductClick: (Int) -> Unit,
) {


    Card(
        modifier = Modifier
            .height(104.dp)
            .fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
        )
    ) {
        Row(
            modifier = Modifier.padding(8.dp), horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            AsyncImage(
                modifier = Modifier.size(85.dp),
                model = productSimple.image,
                contentDescription = null
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                Text(
                    text = productSimple.title,
                    maxLines = 1,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
                Text(
                    text = "$" + productSimple.price,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
                Text(text = "Quantity: ${productSimple.quantity}")
            }

            IconButton(onClick = { onRemoveProductClick(productSimple.id) }) {
                Icon(imageVector = Icons.Outlined.RestoreFromTrash, contentDescription = null)
            }
        }


    }

}
