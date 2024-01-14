package com.example.adminwavesoffood.Adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.adminwavesoffood.Model.PendingModel
import com.example.adminwavesoffood.R
import com.example.adminwavesoffood.databinding.PendingListBinding

class PendingAdapter(var pendingItemList: ArrayList<PendingModel>, var context: Context) :
    RecyclerView.Adapter<PendingAdapter.MyViewHolder>() {

    inner class MyViewHolder(var binding: PendingListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var isAccepted = false
        fun bind(position: Int) {

            var model = pendingItemList[position]
            binding.apply {
                customerName.text = model.customerName
                pendingItemQty.text = model.quantity
                pendingOrderImg.setImageResource(model.foodImg)

                pendingOrderBtn.apply {
                    if (!isAccepted) {
                        text = "Accept"
                    } else {
                        text = "Dispatch"
                    }
                    setOnClickListener {
                        if (!isAccepted) {
                            text = "Dispatch"
                            pendingOrderBtn.setBackgroundResource(R.drawable.un_shape)
                            isAccepted = true
                            showToast("Order is Accepted")
                        } else {
                            pendingItemList.removeAt(position)
                            notifyItemRemoved(adapterPosition)
                            showToast("Order is Dispatch")
                        }
                    }
                }
            }
        }

        fun showToast(message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
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