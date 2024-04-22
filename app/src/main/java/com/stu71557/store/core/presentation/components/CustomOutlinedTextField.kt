package com.stu71557.store.core.presentation.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun CustomOutlinedTextField(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {


    val showPassword = remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = modifier,
        value = text,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation =  if (showPassword.value) VisualTransformation.None else visualTransformation,
        shape = RoundedCornerShape(8.dp),
        trailingIcon = {
            if (visualTransformation == PasswordVisualTransformation()){
                val icon =
                    if (showPassword.value) Icons.Filled.VisibilityOff else Icons.Filled.Visibility

                IconButton(onClick = { showPassword.value = !showPassword.value}) {
                    Icon(imageVector = icon, contentDescription = null)
                }
            }


        },
        label = {
            Text(text = hint)
        },
        colors = OutlinedTextFieldDefaults.colors(
        ),
        singleLine = true
    )
}