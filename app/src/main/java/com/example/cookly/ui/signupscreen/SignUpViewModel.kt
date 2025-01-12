package com.example.cookly.ui.signupscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookly.database.AuthRespository
import com.example.cookly.ui.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: AuthRespository
) : ViewModel() {

    private val _signUpState = MutableStateFlow(SignUpState())
    val signUpState: StateFlow<SignUpState> = _signUpState
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    fun registerUser(email: String, password: String) = viewModelScope.launch {
        repository.registerUser(email, password).collect { result ->
            when (result) {
                is Resource.Success -> {
                    _signUpState.value = SignUpState(isSuccess = "Sign Up Success ")
                    val user = auth.currentUser
                    val userData = hashMapOf(
                        "uid" to user!!.uid,
                        "email" to user!!.email,
                        "photoUrl" to user!!.photoUrl?.toString()
                    )
                    db.collection("users")
                        .document(user.uid)
                        .set(userData)
                        .addOnSuccessListener {
                            Log.d("SignUpViewModel", "User data added to Firestore")
                        }
                        .addOnFailureListener {
                            Log.e("SignUpViewModel", "Error adding user data to Firestore", it)
                        }
                }
                is Resource.Loading -> {
                    _signUpState.value = SignUpState(isLoading = true)
                }
                is Resource.Error -> {
                    _signUpState.value = SignUpState(isError = result.message)
                }
            }
        }
    }
}