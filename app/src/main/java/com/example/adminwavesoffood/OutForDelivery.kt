package com.example.adminwavesoffood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminwavesoffood.Adapter.DeliveryAdapter
import com.example.adminwavesoffood.Model.OrderModel
import com.example.adminwavesoffood.databinding.ActivityOutForDeliveryBinding

class OutForDelivery : AppCompatActivity() {
    val binding: ActivityOutForDeliveryBinding by lazy {
        ActivityOutForDeliveryBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var list = ArrayList<OrderModel>()
        list.add(OrderModel("Jone Doe", "received"))
        list.add(OrderModel("Jane Smith", "notReceived"))
        list.add(OrderModel("Mike Johnson", "Pending"))

        val adapter = DeliveryAdapter(list, this)
        binding.rvOrder.layoutManager = LinearLayoutManager(this)
        binding.rvOrder.adapter = adapter
        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}