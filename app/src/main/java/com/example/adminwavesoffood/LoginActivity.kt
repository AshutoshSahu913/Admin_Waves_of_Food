package com.example.adminwavesoffood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.adminwavesoffood.Model.UserModel
import com.example.adminwavesoffood.databinding.ActivityLoginPageBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class LoginActivity : AppCompatActivity() {

    private var userName: String? = null
    private var nameOfRestaurant: String? = null
    private lateinit var email: String
    private lateinit var password: String

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference


    val binding: ActivityLoginPageBinding by lazy {
        ActivityLoginPageBinding.inflate(layoutInflater)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //initialize firebase auth
        auth = Firebase.auth

        //initialize firebase database
        database = Firebase.database.reference

        binding.loginBtn.setOnClickListener {
            email = binding.inputEmail.text.toString().trim()
            password = binding.inputPassword.text.toString().trim()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please fill All Details", Toast.LENGTH_SHORT).show()
            } else {
                if (password.length < 6) {
                    Toast.makeText(
                        this,
                        "Password should be At least 6 character",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    createUserAccount(email, password)
                }
            }
        }
        binding.goSignUp.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }

    private fun createUserAccount(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                updateUI(user)
                Toast.makeText(this, "user login successfully", Toast.LENGTH_SHORT).show()
            } else {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        Toast.makeText(
                            this,
                            "Account Created and login successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        saveUserData()
                        updateUI(user)
                    } else {
                        Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show()
                        Log.d("Account", "CreateUserAccount:Authentication Failed", task.exception)
                    }
                }
            }
        }
    }

    private fun saveUserData() {
        //get user data
        email = binding.inputEmail.text.toString().trim()
        password = binding.inputPassword.text.toString().trim()

        val user = UserModel(
            name = userName,
            nameOfRestaurant = nameOfRestaurant,
            email = email,
            password = password
        )
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        userId?.let {
            database.child("user").child(it).setValue(user)
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
    }
}