package com.ayesha.mediapp.ui.splash.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ayesha.core.ViewState
import com.ayesha.mediapp.R
import com.ayesha.mediapp.ui.splash.viewmodel.SplashViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun SplashScreen(onNavigate: (Boolean) -> Unit) {
    val splashViewModel :SplashViewModel = hiltViewModel()
    val splashUiState by splashViewModel.splashUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

   when(splashUiState.viewState){
       ViewState.SUCCESS->{
           LaunchedEffect(Unit) {
               coroutineScope.launch {
                   delay(3000)
                   onNavigate(splashUiState.userName.isNotEmpty())
               }
           }
       }
       else->{}
   }
    Box(
        modifier = Modifier
            .fillMaxSize() ,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text =  stringResource(id = R.string.app_name),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
