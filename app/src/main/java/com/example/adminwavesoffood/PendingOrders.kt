package com.example.adminwavesoffood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminwavesoffood.Adapter.PendingAdapter
import com.example.adminwavesoffood.Model.PendingModel
import com.example.adminwavesoffood.databinding.ActivityPendingOrdersBinding

class PendingOrders : AppCompatActivity() {
    val binding: ActivityPendingOrdersBinding by lazy {
        ActivityPendingOrdersBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val list = ArrayList<PendingModel>()
        list.add(PendingModel("Tony Stark", "4", R.drawable.menu1))
        list.add(PendingModel("Jone Vick", "2", R.drawable.menu2))
        list.add(PendingModel("Jame Smith", "6", R.drawable.menu4))
        list.add(PendingModel("Mac Medock", "3", R.drawable.menu3))
        list.add(PendingModel("Dr. Strange", "1", R.drawable.menu5))

        val adapter = PendingAdapter(list, this)
        binding.rvPendingOrder.layoutManager = LinearLayoutManager(this)
        binding.rvPendingOrder.adapter = adapter
    }
}