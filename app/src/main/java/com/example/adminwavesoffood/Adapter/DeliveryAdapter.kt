package com.example.adminwavesoffood.Adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminwavesoffood.Model.OrderDetails
import com.example.adminwavesoffood.databinding.OrderListBinding

class DeliveryAdapter(
    var cNames: MutableList<String>,
    private var moneyStatus: MutableList<Boolean>,
    var context: Context
) :
    RecyclerView.Adapter<DeliveryAdapter.MyViewHolder>() {


    inner class MyViewHolder(var binding: OrderListBinding) :

        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                customerName.text = cNames[position]
                if (moneyStatus[position]) {
                    paymentStatus.text = "Received"
                } else {
                    paymentStatus.text = "Not Received"
                }

                val colorMap = mapOf(
                    true to Color.GREEN, false to Color.RED, "Pending" to Color.GRAY
                )
                paymentStatus.setTextColor(colorMap[moneyStatus[position]] ?: Color.BLACK)
                cardColor.backgroundTintList =
                    ColorStateList.valueOf(colorMap[moneyStatus[position]] ?: Color.BLACK)
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DeliveryAdapter.MyViewHolder {
        var binding = OrderListBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DeliveryAdapter.MyViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return cNames.size
    }
}