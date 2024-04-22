package com.stu71557.store.auth.presentation.auth_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.stu71557.store.auth.domain.model.LoginRequest
import com.stu71557.store.core.presentation.components.CustomOutlinedTextField

@Composable
fun AuthScreen(
    uiState: AuthUiState,
    onClickLogin: (LoginRequest) -> Unit,
    onNavigateToProducts: () -> Unit,
    onNavigateToSignup: () -> Unit
) {

    var username by remember {
        mutableStateOf("mor_2314")
    }
    var password by remember {
        mutableStateOf("83r5^_")
    }
    var showDialog by remember {
        mutableStateOf(false)
    }
    var errorMessage by remember {
        mutableStateOf("")
    }



    LaunchedEffect(key1 = uiState) {
        uiState.error?.let {
            errorMessage = it.message
            showDialog = true
        }
        if (uiState.loginIsSuccessful) {
            onNavigateToProducts()
        }
    }

    AnimatedVisibility(visible = showDialog) {
        AlertDialog(
            title = {
                Text(text = errorMessage)
            },
            text = {
                Text(text = "Use the default login information")
            },
            onDismissRequest = { showDialog = !showDialog },
            confirmButton = {
                Button(onClick = {
                    showDialog = !showDialog
                    username = "mor_2314"
                    password = "83r5^_"
                }) {
                    Text(text = "Reset to default")
                }
            },
        )
    }


    ConstraintLayout {
        val (textHelloAgain, columnTextFields, boxLoading) = createRefs()
        val guide = createGuidelineFromTop(0.2f)


        Text(modifier = Modifier.constrainAs(textHelloAgain) {
                top.linkTo(guide)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }, text = "StorEdu!", fontWeight = FontWeight.Bold, fontSize = 32.sp)

        Column(modifier = Modifier
            .padding(15.dp)
            .constrainAs(columnTextFields) {
                top.linkTo(textHelloAgain.bottom, margin = 15.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
            Text(text = "User name", fontWeight = FontWeight.Medium, fontSize = 19.sp)

            CustomOutlinedTextField(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
                text = username,
                onValueChange = { username = it })

            Text(
                modifier = Modifier.padding(top = 15.dp),
                text = "Password",
                fontWeight = FontWeight.Medium,
                fontSize = 19.sp
            )
            CustomOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                text = password,
                onValueChange = { password = it },
                keyboardType = KeyboardType.Password,
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(modifier = Modifier
                .height(50.dp)
                .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                ),
                shape = RoundedCornerShape(14.dp),
                onClick = {
                    onClickLogin(LoginRequest(username = username, password = password))
                }) {

                Text(text = "Login")
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f),
                    text = "Not a member?",
                    textAlign = TextAlign.End)
                Text(
                    modifier = Modifier.clickable { onNavigateToSignup() },
                    text = "Register now",
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary)
            }
        }

        if (uiState.isLoading) {
            Box(modifier = Modifier
                .constrainAs(boxLoading) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .background(Color.Transparent.copy(alpha = 0.2f))
                .fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}