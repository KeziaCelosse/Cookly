package com.example.cookly.ui.onboarding

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookly.model.UserModel
import com.example.cookly.ui.util.SnackBarState
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor() : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val databaseRef = FirebaseDatabase.getInstance()

    var storageVerificationId: String = ""
    private var isFromLogin = false

    private val _snackBarState = MutableStateFlow(SnackBarState())
    val snackBarState: StateFlow<SnackBarState> = _snackBarState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    var userModel = UserModel()

    fun action(event: OnboardingEvents, context: Context) {
        _isLoading.value = true
        when (event) {
            is OnboardingEvents.LoginUpClick -> loginClick(event.userModel, event.status, context)
            is OnboardingEvents.OtpVerificationClick -> otpVerification(event.otpText, event.status)
            is OnboardingEvents.SignUpClick -> signUpClick(event.userModel, event.status, context)
        }
    }

    private fun loginClick(
        userModel: UserModel,
        status: (status: Boolean) -> Unit,
        context: Context
    ) {
        this.userModel = userModel
        isFromLogin = true
        try {
            viewModelScope.launch(Dispatchers.IO) {
                databaseRef.getReference("users")
                    .orderByChild("mobileNumber")
                    .equalTo(this@OnboardingViewModel.userModel.mobileNumber)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(datasnap: DataSnapshot) {
                            if (datasnap.exists()) {
                                sendOtp(this@OnboardingViewModel.userModel, status, context)
                            } else {
                                _isLoading.value = false
                                Log.e("TAG", "User Dosen't exist.")
                                _snackBarState.value = _snackBarState.value.copy(
                                    show = true,
                                    isError = true,
                                    message = "User Dosen't exist, Please Sign Up."
                                )
                                status(false)
                            }
                        }

                        override fun onCancelled(p0: DatabaseError) {
                            status(false)
                            _isLoading.value = false
                            _snackBarState.value = _snackBarState.value.copy(
                                show = true,
                                isError = true,
                                message = p0.message
                            )
                            Log.e("TAG", "onCancelled: " + p0.message)
                        }
                    })
            }
        } catch (e: Exception) {
            status(false)
            _isLoading.value = false
            _snackBarState.value = _snackBarState.value.copy(
                show = true,
                isError = true,
                message = e.message ?: ""
            )
            Log.e("TAG", "onCancelled: " + e.message)
        }
    }

    private fun signUpClick(
        userModel: UserModel,
        status: (status: Boolean) -> Unit,
        context: Context
    ) {
        this.userModel = userModel
        isFromLogin = false
        viewModelScope.launch(Dispatchers.IO) {
            databaseRef.getReference("users")
                .orderByChild("mobileNumber")
                .equalTo(userModel.mobileNumber)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(datasnap: DataSnapshot) {
                        if (datasnap.exists()) {
                            _isLoading.value = false
                            _snackBarState.value = _snackBarState.value.copy(
                                show = true,
                                isError = true,
                                message = "User Already exist, Please try to login."
                            )
                            Log.e("TAG", "User Already exist.")
                        } else {
                            sendOtp(userModel, status, context)
                        }
                    }

                    override fun onCancelled(p0: DatabaseError) {
                        status(false)
                        _isLoading.value = false
                        Log.e("TAG", "onCancelled: " + p0.message)
                    }
                })
        }
    }

    private fun otpVerification(code: String, status: (status: Boolean) -> Unit) {
        val credentials = PhoneAuthProvider.getCredential(storageVerificationId, code)
        verifyOtp(credentials, status)
    }

    private fun sendOtp(userModel: UserModel, status: (status: Boolean) -> Unit, context: Context) {
        try {
            viewModelScope.launch {
                val number = "+1" + userModel.mobileNumber
                val option = PhoneAuthOptions.newBuilder(auth)
                    .setPhoneNumber(number)
                    .setTimeout(60L, TimeUnit.SECONDS)
                    .setActivity(context as Activity) // Use the passed context
                    .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                            status(true)
                            _isLoading.value = false
                        }

                        override fun onVerificationFailed(e: FirebaseException) {
                            status(false)
                            _isLoading.value = false
                        }

                        override fun onCodeSent(
                            verificationId: String,
                            token: PhoneAuthProvider.ForceResendingToken
                        ) {
                            storageVerificationId = verificationId
                            status(true)
                            _isLoading.value = false
                        }
                    })
                    .build()

                PhoneAuthProvider.verifyPhoneNumber(option)
            }
        } catch (e: Exception) {
            status(false)
            _isLoading.value = false
            Log.e("TAG", "sendOtp: " + e.message)
        }
    }

    private fun verifyOtp(credentials: PhoneAuthCredential, status: (status: Boolean) -> Unit) {
        try {
            viewModelScope.launch {
                auth.signInWithCredential(credentials)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                        Log.d("OnboardingViewModel", "OTP verification successful")
                        if (isFromLogin) {
                            status(true)
                            _isLoading.value = false
                            Log.d("OnboardingViewModel", "isFromLogin: true, status(true) called")
                            return@addOnCompleteListener
                        }
                            viewModelScope.launch {
                                val key = FirebaseAuth.getInstance().currentUser?.uid
                                key?.let {
                                    userModel = userModel.copy(userId = it)
                                    databaseRef.getReference("users").child(it).setValue(userModel)
                                        .addOnSuccessListener {
                                            status(true)
                                            _isLoading.value = false
                                            Log.d("OnboardingViewModel", "User data saved, status(true) called")
                                        }
                                        .addOnFailureListener {
                                            status(false)
                                            _isLoading.value = false
                                            Log.e("OnboardingViewModel", "User data save failed")
                                        }
                                }
                            }
                        } else {
                            status(false)
                            _isLoading.value = false
                            Log.e("OnboardingViewModel", "OTP verification failed")
                        }
                    }
            }
        } catch (e: Exception) {
            _isLoading.value = false
            Log.e("OnboardingViewModel", "verifyOtp: " + e.message)
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}