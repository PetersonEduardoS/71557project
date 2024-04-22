package com.stu71557.store.profile.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.stu71557.store.auth.domain.model.User
import com.stu71557.store.core.presentation.components.CustomOutlinedTextField

@Composable
fun ProfileScreen(
    uiState: ProfileUiState, onSaveUser: (User) -> Unit
) {

    var name by remember {
        mutableStateOf(uiState.user.name)
    }
    var email by remember {
        mutableStateOf(uiState.user.email)
    }
    var password by remember {
        mutableStateOf(uiState.user.password)
    }

    val user = uiState.user.copy(
        name = name, email = email, password = password
    )

    LaunchedEffect(key1 = uiState.user) {
        name = uiState.user.name
        email = uiState.user.email
        password = uiState.user.password
    }


    Box {


        Column(
            modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Spacer(modifier = Modifier.height(56.dp))

            Box(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(100.dp)
                        .clip(RoundedCornerShape(25.dp)),
                    model = "https://thispersondoesnotexist.com/",
                    contentDescription = null
                )
            }

            Text(
                text = "Name",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
            )

            CustomOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                text = name,
                onValueChange = { name = it })

            Text(
                text = "Email Address",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
            )

            CustomOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                text = email,
                onValueChange = { email = it },
                keyboardType = KeyboardType.Email
            )

            Text(
                text = "Password",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
            )

            CustomOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                text = password,
                onValueChange = { password = it },
                keyboardType = KeyboardType.Password,
                visualTransformation = PasswordVisualTransformation()
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Recovery Password",
                textAlign = TextAlign.End,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(),
                onClick = { onSaveUser(user) },
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(text = "Save Now")
            }

        }

        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .background(Color.Transparent.copy(alpha = 0.4f))
                    .fillMaxSize()
            ) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}