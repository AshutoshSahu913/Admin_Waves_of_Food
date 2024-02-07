package com.example.adminwavesoffood.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminwavesoffood.databinding.OrderDetailsItemsBinding

class OrderDetailsAdapter(
    private var context: Context,
    private var food_Name: ArrayList<String>,
    private var food_Img: ArrayList<String>,
    private var food_Price: ArrayList<String>,
    private var food_Qty: ArrayList<Int>
) : RecyclerView.Adapter<OrderDetailsAdapter.viewHolder>() {

    inner class viewHolder(var binding: OrderDetailsItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {

            binding.foodName.text = food_Name[position]

            val uriString = food_Img[position]
            val uri = Uri.parse(uriString)
            Glide.with(context).load(uri).into(binding.foodImg)

            binding.foodPrice.text = food_Price[position]
            binding.foodQty.text = food_Qty[position].toString()


        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderDetailsAdapter.viewHolder {
        val binding = OrderDetailsItemsBinding.inflate(LayoutInflater.from(context), parent, false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderDetailsAdapter.viewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return food_Name.size
    }
}