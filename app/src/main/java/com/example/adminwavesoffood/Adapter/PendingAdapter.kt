package com.example.adminwavesoffood.Adapter

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
    }

    inner class MyViewHolder(var binding: PendingListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var isAccepted = false
        fun bind(position: Int) {

            binding.apply {
                pendingCustomerName.text = customerName[position]
                pendingItemTotalPrice.text = totalPrice[position]
//                pendingItemQty.text = qty[position].toString()
                val uriString = foodImg[position]
                val uri = Uri.parse(uriString)
                Glide.with(context).load(uri).into(pendingOrderImg)
                pendingOrderBtn.apply {
                    if (!isAccepted) {
                        text = "Accept"
                    } else {
                        text = "Dispatch"
                    }
                    setOnClickListener {
                        if (!isAccepted) {
                            text = "Dispatch"
                            setBackgroundResource(R.drawable.un_shape)
                            isAccepted = true
                            showToast("Order is Accepted")
                        } else {
                            customerName.removeAt(position)
                            totalPrice.removeAt(position)
                            foodImg.removeAt(position)
                            notifyItemRemoved(position)
                            isAccepted = false
                            showToast("Order is Dispatch")
                        }
                    }
                    itemView.setOnClickListener {
                        itemClicked.onItemClickListener(position)
                    }
                }
            }

        }
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