package com.example.adminwavesoffood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminwavesoffood.Adapter.PendingAdapter
import com.example.adminwavesoffood.Model.OrderDetails
import com.example.adminwavesoffood.databinding.ActivityPendingOrdersBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PendingOrders : AppCompatActivity(), PendingAdapter.OnItemClicked {
    val binding: ActivityPendingOrdersBinding by lazy {
        ActivityPendingOrdersBinding.inflate(layoutInflater)
    }

    private var listOfName: ArrayList<String> = arrayListOf()

    //    private var listOfTotalQty: MutableList<String> = mutableListOf()
    private var listOfTotalPrice: ArrayList<String> = arrayListOf()
    private var listOfImgFirstFoodOrder: ArrayList<String> = arrayListOf()

    //    private var listOfImgFirstOrder: ArrayList<String> = arrayListOf()
    private var listOfOrderItem: ArrayList<OrderDetails> = arrayListOf()
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseOrderDetails: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //initialization of database
        database = FirebaseDatabase.getInstance()
        databaseOrderDetails = database.reference.child("OrderDetails")

        getOrderDetails()

        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    private fun getOrderDetails() {
        databaseOrderDetails.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (orderSnapshot in snapshot.children) {
                    val orderDetails = orderSnapshot.getValue(OrderDetails::class.java)
                    orderDetails?.let {
                        listOfOrderItem.add(it)
                    }
                }
                addDataToListForRecyclerView(listOfOrderItem)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun addDataToListForRecyclerView(listOfOrderItem: ArrayList<OrderDetails>) {
        for (orderItem in listOfOrderItem) {
            //add data to respective list for population the recyclerview
            orderItem.userName?.let { listOfName.add(it) }
            orderItem.totalPrice?.let { listOfTotalPrice.add(it) }
            orderItem.foodImages?.filterNot { it.isEmpty() }?.forEach {
                listOfImgFirstFoodOrder.add(it)
            }
        }
        setAdapter(listOfName, listOfTotalPrice, listOfImgFirstFoodOrder)
    }

    private fun setAdapter(
        listOfName: ArrayList<String>,
        listOfTotalPrice: ArrayList<String>,
        listOfImgFirstFoodOrder: ArrayList<String>
    ) {
        val adapter = PendingAdapter(
            listOfName,
            listOfTotalPrice,
            listOfImgFirstFoodOrder,
            this,
            this
        )
        binding.rvPendingOrder.layoutManager = LinearLayoutManager(this)
        binding.rvPendingOrder.adapter = adapter


    }


    override fun onItemClickListener(position: Int) {
        val intent = Intent(this, OrderDetailsActivity::class.java)
        val userOderDetails = listOfOrderItem[position]
        intent.putExtra("UserOrderDetails", userOderDetails)
        startActivity(intent)
    }

    override fun onItemAcceptClickListener(position: Int) {
        //handle item acceptance and update database
        val childItemPushKey = listOfOrderItem[position].itemPushKey
        val clickItemOrderReference = childItemPushKey?.let {
            database.reference.child("OrderDetails").child(it)
        }
        clickItemOrderReference?.child("AcceptedOrder")?.setValue(true)
        updateOrderAcceptStatus(position)

    }

    private fun updateOrderAcceptStatus(position: Int) {
        //update order acceptance in user's buyHistory and OrderDetails
        val userIdOfClickedItem = listOfOrderItem[position].userUid
        val pushKeyOfClickedItem = listOfOrderItem[position].itemPushKey
        val buyHistoryReference =
            database.reference.child("user").child(userIdOfClickedItem!!).child("BuyHistory")
                .child(pushKeyOfClickedItem!!)
        buyHistoryReference.child("AcceptedOrder").setValue(true)
        databaseOrderDetails.child(pushKeyOfClickedItem).child("AcceptedOrder").setValue(true)
    }

    override fun onItemDispatchClickListener(position: Int) {
        val dispatchItemPushKey = listOfOrderItem[position].itemPushKey
        val dispatchItemOrderReference =
            database.reference.child("CompleteOrder ").child(dispatchItemPushKey!!)
        dispatchItemOrderReference.setValue(listOfOrderItem[position])
            .addOnSuccessListener {
                deleteThisItemFromOrderDetails(dispatchItemPushKey)
            }

    }

    private fun deleteThisItemFromOrderDetails(dispatchItemPushKey: String) {
        val orderDetailsItemReference =
            database.reference.child("OrderDetails").child(dispatchItemPushKey)
        orderDetailsItemReference.removeValue()
            .addOnSuccessListener {
                Toast.makeText(this, "Order is Dispatched", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Order is not Dispatched", Toast.LENGTH_SHORT).show()
            }
    }

}