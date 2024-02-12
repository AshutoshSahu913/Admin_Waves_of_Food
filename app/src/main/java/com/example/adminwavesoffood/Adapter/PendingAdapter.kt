package com.example.adminwavesoffood.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminwavesoffood.R
import com.example.adminwavesoffood.databinding.PendingListBinding

class PendingAdapter(
    private var customerName: MutableList<String>,
    private var totalPrice: MutableList<String>,
    private var foodImg: MutableList<String>,
//    private val qty: MutableList<Int>,
    var context: Context,
    private val itemClicked: OnItemClicked
) :
    RecyclerView.Adapter<PendingAdapter.MyViewHolder>() {
    interface OnItemClicked {
        fun onItemClickListener(position: Int)
        fun onItemAcceptClickListener(position: Int)
        fun onItemDispatchClickListener(position: Int)
    }

    inner class MyViewHolder(var binding: PendingListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var isAccepted = false
        @SuppressLint("SetTextI18n")
        fun bind(position: Int) {
            binding.apply {
                pendingCustomerName.text = customerName[position]
                pendingItemTotalPrice.text = totalPrice[position]
//                pendingItemQty.text = qty[position].toString()
                val uriString = foodImg[position]
                val uri = Uri.parse(uriString)
                Glide.with(context).load(uri).into(pendingOrderImg)

                pendingOrderBtn.apply {
                    text = if (!isAccepted) {
                        "Accept"
                    } else {
                        "Dispatch"
                    }
                    setOnClickListener {
                        setBackgroundResource(R.drawable.un_shape)
                        if (!isAccepted) {
                            text = "Dispatch"
                            isAccepted = true
                            showToast("Order is Accepted")
                            itemClicked.onItemAcceptClickListener(position)
                        } else {
//                            isAccepted = false
                            removeItem(position)
                            showToast("Order is Dispatch")
                            itemClicked.onItemDispatchClickListener(position)
                        }
                    }
                }
                itemView.setOnClickListener {
                    itemClicked.onItemClickListener(position)
                }
            }
        }
    }
    fun removeItem(position: Int) {
        customerName.removeAt(position)
        totalPrice.removeAt(position)
        foodImg.removeAt(position)
        notifyItemRemoved(position)
    }

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingAdapter.MyViewHolder {
        val binding = PendingListBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PendingAdapter.MyViewHolder, position: Int) {
        holder.bind(position)

    }

    override fun getItemCount(): Int {
        return customerName.size
    }
}