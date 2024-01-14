package com.example.adminwavesoffood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Adapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminwavesoffood.Adapter.ItemAdapter
import com.example.adminwavesoffood.Model.ItemModel
import com.example.adminwavesoffood.databinding.ActivityAllItemBinding

class All_ItemActivity : AppCompatActivity() {
    val binding: ActivityAllItemBinding by lazy {
        ActivityAllItemBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var list = ArrayList<ItemModel>()
        list.add(ItemModel(R.drawable.menu1, "Donuts", "₹ 99", 1))
        list.add(ItemModel(R.drawable.menu2, "Salad", "₹ 69", 2))
        list.add(ItemModel(R.drawable.menu3, "Ice Cream", "₹ 49", 2))
        list.add(ItemModel(R.drawable.menu4, "Soop", "₹ 69", 3))
        list.add(ItemModel(R.drawable.menu5, "Pasta", "₹ 99", 1))

        var adapter = ItemAdapter(list, this)
        binding.rvItemList.layoutManager = LinearLayoutManager(this)
        binding.rvItemList.adapter = adapter

        val Adapter = ItemAdapter(list, this)

        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}