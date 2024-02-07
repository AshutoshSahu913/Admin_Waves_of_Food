package com.example.adminwavesoffood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.adminwavesoffood.Model.UserModel
import com.example.adminwavesoffood.databinding.ActivityRegisterBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class RegisterActivity : AppCompatActivity() {

    private lateinit var userName: String
    private lateinit var nameOfRestaurant: String
    private lateinit var email: String
    private lateinit var password: String

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //Initialize firebase auth
        auth = Firebase.auth

        //Initialize firebase Database
        database = Firebase.database.reference


        binding.createAccountBtn.setOnClickListener {
            //get Text form EditText
            email = binding.inputEmail.text.toString().trim()
            userName = binding.inputName.text.toString().trim()
            nameOfRestaurant = binding.inputRestaurantName.text.toString().trim()
            password = binding.inputPassword.text.toString().trim()

            if (userName.isBlank() || nameOfRestaurant.isBlank() || email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show()
            } else {
                createAccount(email, password)
            }
        }

        binding.alreadyHaveBtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        val locationList = arrayOf("Bhopal", "Indore", "Jaipur", "Odisha", "Mumbai", "Delhi")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, locationList)
        val autoCompleteTextView = binding.listOfLocation
        autoCompleteTextView.setAdapter(adapter)
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_SHORT).show()

                //save user data
                saveUserData()

                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Account Creation Failed", Toast.LENGTH_SHORT).show()
                Log.d("Account", "CreateAccount : Failure", task.exception)
            }

        }
    }

    //save data into database
    private fun saveUserData() {
        //get Text form EditText
        email = binding.inputEmail.text.toString().trim()
        userName = binding.inputName.text.toString().trim()
        nameOfRestaurant = binding.inputRestaurantName.text.toString().trim()
        password = binding.inputPassword.text.toString().trim()

        //create user name
        val user = UserModel(
            name = userName,
            nameOfRestaurant = nameOfRestaurant,
            email = email,
            password = password
        )
        //create unique userid
        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        //save user ka data to the firebase ke database
        database.child("userAdmin").child(userId).setValue(user)

    }
}