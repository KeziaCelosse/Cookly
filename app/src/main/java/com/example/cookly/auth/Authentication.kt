package com.example.cookly.auth

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import com.example.cookly.R
import com.example.cookly.navigation.Screen
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

object Authentication {
    val auth: FirebaseAuth = Firebase.auth
    @SuppressLint("StaticFieldLeak")
    val firestore = Firebase.firestore

    fun register(
        username: String,
        email: String,
        password: String,
        navController: NavHostController,
        context: android.content.Context
    ) {
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{ task->
                if(task.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser

                    val profileUpdate = UserProfileChangeRequest.Builder()
                        .setDisplayName(username)
                        .build()

                    user?.updateProfile(profileUpdate)
                        ?.addOnCompleteListener{ task1->
                            if(task1.isSuccessful){
                                user.let {
                                    val userData = hashMapOf(
                                        "username" to username,
                                        "email" to email

                                    )
                                    firestore.collection("users").document(it.uid)
                                        .set(userData)
                                        .addOnSuccessListener {
                                            navController.navigate(Screen.Homepage.route) {
                                                popUpTo(Screen.MainCookScreen.route) { inclusive = true }
                                                popUpTo(Screen.Register.route) { inclusive = true }
                                            }
                                            Toast.makeText(
                                                context,
                                                context.getString(R.string.regsuc),
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                        .addOnFailureListener {
                                            Toast.makeText(
                                                context,
                                                context.getString(R.string.regfail),
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                }
                            }
                        }
                }
                else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.regfail),
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
    }
    fun login(username: String, password: String, navController: NavHostController, context: Context) {

        auth.signInWithEmailAndPassword(username,password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser
                    user?.let {
                        firestore.collection("users").document(it.uid)
                            .get()
                            .addOnSuccessListener { document ->
                                if (document != null && document.exists()) {
                                    val sharedPreferences = context.getSharedPreferences(
                                        "user_prefs", Context.MODE_PRIVATE)
                                    val editor = sharedPreferences.edit()
                                    editor.apply()

                                    navController.navigate(Screen.Homepage.route) {
                                        popUpTo(Screen.MainCookScreen.route) { inclusive = true }
                                        popUpTo(Screen.Login.route) { inclusive = true }
                                    }
                                    Toast.makeText(
                                        context,
                                        context.getString(R.string.logsuc),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Gagal mendapatkan data pengguna",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            }
                            .addOnFailureListener {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.logfail),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    }
                } else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.logfail),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}