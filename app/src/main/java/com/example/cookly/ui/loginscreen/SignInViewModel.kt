package com.example.cookly.ui.loginscreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.cookly.database.AuthRespository
import com.example.cookly.navigation.Screen
import com.example.cookly.ui.util.Resource
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: AuthRespository
) : ViewModel() {

    val _signInState = Channel<SignInState>()
    val signInState = _signInState.receiveAsFlow()


    fun loginUser(email: String, password: String, navController: NavController) {
        viewModelScope.launch {
            repository.loginUser(email, password).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _signInState.send(SignInState(isSuccess = "Sign In Success "))
                        navController.navigate(Screen.Homepage.route)
                        }
                        is Resource.Loading -> {
                            _signInState.send(SignInState(isLoading = true))
                        }
                        is Resource.Error -> {

                            _signInState.send(SignInState(isError = result.message))
                        }
                    }

                }
            }

        }
    }