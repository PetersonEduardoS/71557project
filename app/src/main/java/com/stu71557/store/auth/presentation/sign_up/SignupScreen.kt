package com.stu71557.store.auth.presentation.sign_up

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.stu71557.store.auth.domain.model.User
import com.stu71557.store.core.presentation.components.CustomOutlinedTextField


@Composable
fun SignupScreen(
    uiState: SignupUiState, onClickSignup: (User) -> Unit, onNavigateToProducts: () -> Unit
) {
    var username by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var confirmPassword by remember {
        mutableStateOf("")
    }
    var showDialog by remember {
        mutableStateOf(false)
    }
    var showDialogConfirmPassword by remember {
        mutableStateOf(false)
    }
    var errorMessage by remember {
        mutableStateOf("")
    }

    val user = User(
        name = username, email = email, password = password, remember = true
    )

    if (showDialogConfirmPassword) {
        AlertDialog(title = {
            Text(text = "Error")
        },
            text = {
                Text(text = "Apologies, the passwords do not match. Please ensure you've entered them correctly and try again.")
            },
            onDismissRequest = { showDialogConfirmPassword = !showDialogConfirmPassword },
            confirmButton = {
                Button(onClick = { showDialogConfirmPassword = !showDialogConfirmPassword }) {
                    Text(text = "Ok")
                }
            })
    }


    LaunchedEffect(key1 = uiState) {
        uiState.error?.let {
            errorMessage = it.message
            showDialog = true
        }
        if (uiState.signupIsSuccessful) {
            onNavigateToProducts()
        }
    }

    AnimatedVisibility(visible = showDialog) {
        AlertDialog(
            title = {
                Text(text = errorMessage)
            },
            text = {
                Text(text = "An error occurred during your authentication")
            },
            onDismissRequest = { showDialog = !showDialog },
            confirmButton = {
                Button(onClick = { showDialog = !showDialog }) {
                    Text(text = "Ok")
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
        }, text = "Let's create an account for you", fontWeight = FontWeight.Bold, fontSize = 22.sp)

        Column(modifier = Modifier
            .padding(15.dp)
            .constrainAs(columnTextFields) {
                top.linkTo(textHelloAgain.bottom, margin = 15.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            verticalArrangement = Arrangement.spacedBy(10.dp)) {

            CustomOutlinedTextField(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
                hint = "User name",
                text = username,
                onValueChange = { username = it })

            CustomOutlinedTextField(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
                hint = "Email",
                text = email,
                keyboardType = KeyboardType.Email,
                onValueChange = { email = it })


            CustomOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                hint = "Password",
                text = password,
                onValueChange = { password = it },
                keyboardType = KeyboardType.Password,
                visualTransformation = PasswordVisualTransformation()
            )
            CustomOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                hint = "Confirm Password",
                text = confirmPassword,
                onValueChange = { confirmPassword = it },
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
                    if (password == confirmPassword) {
                        onClickSignup(user)
                    } else {
                        showDialogConfirmPassword = !showDialogConfirmPassword
                    }

                }) {
                Text(text = "Signup")

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