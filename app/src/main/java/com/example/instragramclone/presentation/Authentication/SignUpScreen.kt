package com.example.instragramclone.presentation.Authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.instragramclone.R
import com.example.instragramclone.presentation.Toast
import com.example.instragramclone.util.Response
import com.example.instragramclone.util.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navController: NavHostController,  viewModel: AuthenticationViewModel){
    Box(modifier= Modifier.fillMaxSize()){
        Column(modifier= Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .verticalScroll(
                rememberScrollState()
            ),
        horizontalAlignment = Alignment.CenterHorizontally){
            val userNameState = remember{
                mutableStateOf("")
            }

            val emailState = remember{
                mutableStateOf("")
            }
            val passwordState = remember{
                mutableStateOf("")
            }
            Image(
                painter = painterResource(id = R.drawable.ig_logo),
                contentDescription = "LoginScreen Logo",
                modifier = Modifier
                    .width(26.dp)
                    .padding(top = 16.dp)
                    .padding(8.dp)
            )
            Text(
                text = "Sign Up",
                modifier = Modifier.padding(10.dp),
                fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif
            )
            OutlinedTextField(value = userNameState.value, onValueChange ={
                userNameState.value = it
            },
                modifier=Modifier.padding(10.dp),
                label = {
                    Text(text = "Enter Your Username: ")
                }
            )
            OutlinedTextField(value = emailState.value, onValueChange ={
                emailState.value = it
            },
                modifier=Modifier.padding(10.dp),
                label = {
                    Text(text = "Enter Your Email: ")
                }
            )
            OutlinedTextField(value = passwordState.value, onValueChange ={
                passwordState.value = it
            },
                modifier=Modifier.padding(10.dp),
                label = {
                    Text(text = "Enter Your Password: ")
                },
                visualTransformation = PasswordVisualTransformation()
            )
            Button(onClick = {
                viewModel.signUp(
                    email = emailState.value,
                    password = passwordState.value,
                    username = userNameState.value
                )
            }, modifier=Modifier.padding(8.dp)
            ) {
                Text(text="Sign Up")
                when(val response = viewModel.signUpState.value){
                    is Response.Loading ->{
                        CircularProgressIndicator(
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    is Response.Success ->{
                        if(response.data){
                            LaunchedEffect(key1 = true) {
                                navController.navigate(Screens.ProfileScreen.route) {
                                    popUpTo(Screens.LoginScreen.route) {
                                        inclusive = true
                                    }
                                }
                            }
                        }
                        else{
                            Toast(message = "Sign Up failed")
                        }
                    }
                    is Response.Error ->{
                        Toast(message = response.message)
                    }
                }
            }
            Text(text = "Already a User? Sign IN ", color= Color.Blue, modifier= Modifier
                .padding(8.dp)
                .clickable {
                    navController.navigate(route = Screens.LoginScreen.route) {
                        launchSingleTop = true
                    }
                })
        }
    }
}