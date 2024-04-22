package com.stu71557.store.core.presentation.customdrawer.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.stu71557.store.core.Screen

@Composable
fun CustomDrawer(
    selectedScreen: Screen,
    onScreenClick: (Screen) -> Unit,
    onCloseClick: () -> Unit
) {

    val screens = listOf(Screen.Profile, Screen.Cart)

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(fraction = 0.6f)
            .padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        ){
            IconButton(onClick = onCloseClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = null
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

            AsyncImage(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(40.dp))
                    .height(10.dp),
                model = "https://thispersondoesnotexist.com/",
                contentDescription = null)
            
            Text(
                text = "kevinryan",
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp)
        Spacer(modifier = Modifier.height(40.dp))

        screens.forEach { screen ->
            ScreenItemView(
                screen = screen,
                selected = screen == selectedScreen,
                onClick = { onScreenClick(screen) }
            )
            Spacer(modifier = Modifier.height(4.dp))

        }

    }
    
}