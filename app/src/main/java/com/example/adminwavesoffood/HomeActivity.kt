package com.example.adminwavesoffood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.adminwavesoffood.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.apply {
            addMenu.setOnClickListener {
                startActivity(Intent(this@HomeActivity, AddItem::class.java))
            }
            addMenuItem.setOnClickListener {
                startActivity(Intent(this@HomeActivity, All_ItemActivity::class.java))
            }
            orderDispatch.setOnClickListener {
                startActivity(Intent(this@HomeActivity, OutForDelivery::class.java))
            }

            addNewUser.setOnClickListener {
                startActivity(Intent(this@HomeActivity, AddNewUser::class.java))
            }
            profile.setOnClickListener {
                startActivity(Intent(this@HomeActivity, AdminProfile::class.java))
            }
            logout.setOnClickListener {
                startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
                finish()
            }
        }
    }
}