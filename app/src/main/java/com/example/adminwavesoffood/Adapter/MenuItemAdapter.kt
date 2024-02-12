package com.example.adminwavesoffood.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminwavesoffood.Model.ItemModel
import com.example.adminwavesoffood.databinding.ItemListBinding
import com.google.firebase.database.DatabaseReference

class MenuItemAdapter(
    var itemList: ArrayList<ItemModel>,
    var context: Context,
    var databaseReference: DatabaseReference,
    private val onDeleteClickListener:(position:Int) ->Unit
) :
    RecyclerView.Adapter<MenuItemAdapter.MyViewHolder>() {
    private val itemQuantities = IntArray(itemList.size) { 1 }

    inner class MyViewHolder(var binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantities[position]
                val menuItem = itemList[position]
                val uriString = menuItem.foodImg
                val uri = Uri.parse(uriString)

                foodName.text = menuItem.foodName
                foodPrice.text = menuItem.foodPrice
                foodQty.text = quantity.toString()
                Glide.with(context).load(uri).into(foodImg)

                minusBtn.setOnClickListener {
                    deceaseQuantity(position)
                }
                plusBtn.setOnClickListener {
                    increaseQuantity(position)

                }
                deleteBtn.setOnClickListener {
//                    val itemPosition = adapterPosition
//                    if (itemPosition != RecyclerView.NO_POSITION) {
//                        deleteItems(itemPosition)
//                    }
                    onDeleteClickListener(position)
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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MenuItemAdapter.MyViewHolder {
        var binding = ItemListBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuItemAdapter.MyViewHolder, position: Int) {
        holder.bind(position)

    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}