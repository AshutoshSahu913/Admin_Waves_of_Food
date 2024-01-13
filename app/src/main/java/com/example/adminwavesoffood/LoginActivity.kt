package com.example.adminwavesoffood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.adminwavesoffood.databinding.ActivityLoginPageBinding

class LoginActivity : AppCompatActivity() {
    val binding: ActivityLoginPageBinding by lazy {
        ActivityLoginPageBinding.inflate(layoutInflater)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)
        binding.loginBtn.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))

        }
        binding.goSignUp.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }
    }
}