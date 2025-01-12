package com.example.cookly.ui.onboarding

import com.example.cookly.model.UserModel


sealed interface OnboardingEvents {


    data class SignUpClick(val userModel: UserModel, val status :  (status: Boolean) -> Unit) : OnboardingEvents
    data class LoginUpClick(val userModel: UserModel, val status :  ( status: Boolean) -> Unit) : OnboardingEvents
    data class OtpVerificationClick(val otpText: String, val status :  ( status: Boolean) -> Unit) : OnboardingEvents
}