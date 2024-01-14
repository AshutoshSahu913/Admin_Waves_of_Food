package com.example.adminwavesoffood.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminwavesoffood.Model.ItemModel
import com.example.adminwavesoffood.databinding.ItemListBinding

class ItemAdapter(var itemList: ArrayList<ItemModel>, var context: Context) :
    RecyclerView.Adapter<ItemAdapter.MyViewHolder>() {
    private val itemQuantities = IntArray(itemList.size) { 1 }

    inner class MyViewHolder(var binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {

            binding.apply {

                val quantity = itemQuantities[position]
                foodQty.text = quantity.toString()
                minusBtn.setOnClickListener {
                    deceaseQuantity(position)
                }
                plusBtn.setOnClickListener {
                    increaseQuantity(position)

                }
                deleteBtn.setOnClickListener {
                    val itemPosition = adapterPosition
                    if (itemPosition != RecyclerView.NO_POSITION) {
                        deleteItems(itemPosition)
                    }
                }
            }
        }

        private fun deceaseQuantity(position: Int) {
            if (itemQuantities[position] > 1) {
                itemQuantities[position]--
                binding.foodQty.text = itemQuantities[position].toString()

            }
        }

        private fun increaseQuantity(position: Int) {
            if (itemQuantities[position] < 10) {
                itemQuantities[position]++
                binding.foodQty.text = itemQuantities[position].toString()
            }
        }

        private fun deleteItems(position: Int) {
            itemList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemList.size)
        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.MyViewHolder {
        var binding = ItemListBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemAdapter.MyViewHolder, position: Int) {
        holder.bind(position)

        var model = itemList[position]
        holder.binding.foodName.text = model.foodName
//        holder.binding.foodDes.text = model.foodDes
        holder.binding.foodImg.setImageResource(model.foodImg)
        holder.binding.foodPrice.text = model.foodPrice
        holder.binding.foodQty.text = model.foodQty.toString()
    }


    override fun getItemCount(): Int {
        return itemList.size
    }
}