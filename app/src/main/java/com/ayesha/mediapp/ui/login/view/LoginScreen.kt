package com.ayesha.mediapp.ui.login.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ayesha.core.ViewState
import com.ayesha.mediapp.R
import com.ayesha.mediapp.ui.common.BaseAnimatedLoader
import com.ayesha.mediapp.ui.common.BaseToast
import com.ayesha.mediapp.ui.common.ToastType
import com.ayesha.mediapp.ui.login.viewmodel.LoginViewModel


@Composable
fun LoginScreen(onNavigate: () -> Unit={}) {
    val loginViewModel: LoginViewModel = hiltViewModel()
    val uiState by loginViewModel.loginUiState.collectAsStateWithLifecycle()
    Box {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(vertical = 16.dp),
                text = stringResource(id = R.string.app_name),
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.padding(vertical = 16.dp),
                text = stringResource(R.string.welcome_to_medi_app),
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            OutlinedTextField(modifier = Modifier.fillMaxWidth().testTag("UserName"),
                value = uiState.userName,
                onValueChange = {
                    loginViewModel.onUserNameChange(it)
                },
                label = {
                    Text(
                        text = stringResource(R.string.username),
                        color = Color.Black,
                        fontSize = 16.sp,
                    )
                })
            OutlinedTextField(modifier = Modifier
                .fillMaxWidth().testTag("Email")
                .padding(vertical = 16.dp),
                value = uiState.email,
                onValueChange = {
                    loginViewModel.onEmailChange(it)
                },
                label = {
                    Text(
                        text = stringResource(R.string.email),
                        color = Color.Black,
                        fontSize = 16.sp,
                    )
                })
            Button(
                modifier = Modifier
                    .fillMaxWidth().testTag("Login")
                    .height(80.dp)
                    .padding(vertical = 16.dp),
                onClick = {
                    loginViewModel.onLoginButtonClick()
                }) {
                Text(
                    text = stringResource(R.string.login),
                    color = Color.White,
                    fontSize = 16.sp,
                )
            }
        }
        when (uiState.viewState) {
            ViewState.LOADING -> {
                BaseAnimatedLoader()
            }

            ViewState.SUCCESS -> {
                loginViewModel.clearUiState()
                LaunchedEffect(key1 = Unit) {
                    onNavigate.invoke()
                }
            }
            ViewState.ERROR -> {
                BaseToast(ToastType.ERROR, stringResource(id = uiState.errorMessage))
            }

            ViewState.EMPTY -> {}
        }
    }
}
