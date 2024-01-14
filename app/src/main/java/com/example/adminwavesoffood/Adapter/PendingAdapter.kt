package com.example.adminwavesoffood.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminwavesoffood.Model.PendingModel
import com.example.adminwavesoffood.databinding.PendingListBinding

class PendingAdapter(var pendingItemList: ArrayList<PendingModel>, var context: Context) :
    RecyclerView.Adapter<PendingAdapter.MyViewHolder>() {

    inner class MyViewHolder(var binding: PendingListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            var model = pendingItemList[position]
            binding.apply {
                customerName.text = model.customerName
                pendingItemQty.text = model.quantity
                pendingOrderImg.setImageResource(model.foodImg)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingAdapter.MyViewHolder {
        var binding = PendingListBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PendingAdapter.MyViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return pendingItemList.size
    }
}