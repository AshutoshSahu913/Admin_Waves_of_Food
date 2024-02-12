package com.example.adminwavesoffood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.adminwavesoffood.Model.UserModel
import com.example.adminwavesoffood.databinding.ActivityAdminProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdminProfile : AppCompatActivity() {
    val binding: ActivityAdminProfileBinding by lazy {
        ActivityAdminProfileBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var adminReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        adminReference = database.reference.child("user")


        binding.apply {
            edName.isEnabled = false
            edAddress.isEnabled = false
            edPhone.isEnabled = false
            edEmail.isEnabled = false
            edPassword.isEnabled = false
            saveInfoBtn.isEnabled = false

            var isEnable = true
            clickHere.setOnClickListener {
                isEnable = !isEnable

                edName.isEnabled = isEnable
                edAddress.isEnabled = isEnable
                edPhone.isEnabled = isEnable
                edEmail.isEnabled = isEnable
                edPassword.isEnabled = isEnable
                if (isEnable) {
                    edName.requestFocus()
                }
                saveInfoBtn.isEnabled = isEnable
            }
            saveInfoBtn.setOnClickListener {
                updateUserData()
            }

            backBtn.setOnClickListener {
                finish()
            }
        }
        retrieveUserData()
    }

    private fun retrieveUserData() {
        val currentUserUid = auth.currentUser?.uid
        if (currentUserUid != null) {
            val userRef = adminReference.child(currentUserUid)
            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val ownerName = snapshot.child("name").value
                        val ownerEmail = snapshot.child("email").value
                        val ownerPassword = snapshot.child("password").value
                        val ownerAddress = snapshot.child("address").value
                        val ownerPhone = snapshot.child("phone").value

                        setDataToTextView(
                            ownerName,
                            ownerEmail,
                            ownerPassword,
                            ownerAddress,
                            ownerPhone
                        )
                    }

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        }

    }

    private fun setDataToTextView(
        ownerName: Any?,
        ownerEmail: Any?,
        ownerPassword: Any?,
        ownerAddress: Any?,
        ownerPhone: Any?
    ) {

        binding.edName.setText(ownerName.toString())
        binding.edEmail.setText(ownerEmail.toString())
        binding.edPassword.setText(ownerPassword.toString())
        binding.edPhone.setText(ownerPhone.toString())
        binding.edAddress.setText(ownerAddress.toString())

    }

    private fun updateUserData() {
        val updateName = binding.edName.text.toString()
        val updateEmail = binding.edEmail.text.toString()
        val updatePassword = binding.edPassword.text.toString()
        val updatePhone = binding.edPhone.text.toString()
        val updateAddress = binding.edAddress.text.toString()

        val currentUserId = auth.currentUser?.uid
        if (currentUserId != null) {
            val userRef = adminReference.child(currentUserId)
            userRef.child("name").setValue(updateName)
            userRef.child("email").setValue(updateEmail)
            userRef.child("password").setValue(updatePassword)
            userRef.child("address").setValue(updateAddress)
            userRef.child("phone").setValue(updatePhone)


            Toast.makeText(this, "Profile updated SuccessFull!", Toast.LENGTH_SHORT).show()

            //update the email and password for firebase Authentication
            auth.currentUser?.updateEmail(updateEmail)
            auth.currentUser?.updatePassword(updatePassword)
        } else {
            Toast.makeText(this, "Profile updated Failed!", Toast.LENGTH_SHORT).show()
        }
    }

}