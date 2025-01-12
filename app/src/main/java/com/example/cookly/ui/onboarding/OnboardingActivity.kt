package com.example.cookly.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.cookly.MainActivityChat
import com.example.cookly.model.UserModel
import com.google.firebase.auth.FirebaseAuth

class OnboardingActivity : ComponentActivity() {

    val onboardingViewModel: OnboardingViewModel by viewModels<OnboardingViewModel>()

    var currentUser = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // onboardingViewModel.provideActivity(this) // Hapus baris ini
        setContent {
            if (currentUser != null) {
                this.startActivity(Intent(this, MainActivityChat::class.java).apply {

                })
            }

            OnboardingNavigation(onboardingViewModel)
        }

        // Contoh pemanggilan action (Login)
        onboardingViewModel.action(
            OnboardingEvents.LoginUpClick(UserModel(mobileNumber = "1234567890")) { status ->
                // Handle status
                Log.d("OnboardingActivity", "Login status: $status")
            },
            this // Kirimkan context
        )

        // Contoh pemanggilan action (Sign Up)
        onboardingViewModel.action(
            OnboardingEvents.SignUpClick(UserModel(mobileNumber = "1234567890")) { status ->
                // Handle status
                Log.d("OnboardingActivity", "Sign Up status: $status")
            },
            this // Kirimkan context
        )
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}