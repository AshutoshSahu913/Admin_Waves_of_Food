package com.example.adminwavesoffood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.adminwavesoffood.databinding.ActivityAdminProfileBinding

class AdminProfile : AppCompatActivity() {
    val binding: ActivityAdminProfileBinding by lazy {
        ActivityAdminProfileBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            edName.isEnabled = false
            edAddress.isEnabled = false
            edPhone.isEnabled = false
            edEmail.isEnabled = false
            edPassword.isEnabled = false

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
            }

            backBtn.setOnClickListener {
                finish()
            }
        }
    }
}