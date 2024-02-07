package com.example.adminwavesoffood.Adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminwavesoffood.Model.OrderDetails
import com.example.adminwavesoffood.databinding.OrderListBinding

class DeliveryAdapter(var orderList: ArrayList<OrderDetails>, var context: Context) :
    RecyclerView.Adapter<DeliveryAdapter.MyViewHolder>() {


    inner class MyViewHolder(var binding: OrderListBinding) :

        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                var model = orderList[position]
                customerName.text = model.userName
                paymentStatus.text = model.orderAccepted.toString()

                val colorMap = mapOf(
                    "received" to Color.GREEN, "notReceived" to Color.RED, "Pending" to Color.GRAY
                )
                paymentStatus.setTextColor(colorMap[model.orderAccepted.toString()] ?: Color.BLACK)
                cardColor.backgroundTintList =
                    ColorStateList.valueOf(colorMap[model.orderAccepted.toString()] ?: Color.BLACK)
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
        return orderList.size
    }
}