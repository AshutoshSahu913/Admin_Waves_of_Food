package com.example.adminwavesoffood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminwavesoffood.Adapter.DeliveryAdapter
import com.example.adminwavesoffood.Model.OrderDetails
import com.example.adminwavesoffood.databinding.ActivityOutForDeliveryBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class OutForDelivery : AppCompatActivity() {
    val binding: ActivityOutForDeliveryBinding by lazy {
        ActivityOutForDeliveryBinding.inflate(layoutInflater)
    }

    private lateinit var database: FirebaseDatabase
    private var listOfCompleteOrderList: ArrayList<OrderDetails> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //retrieve and display completed order
        retrieveCompleteOrderDetails()

        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    private fun retrieveCompleteOrderDetails() {
        //Initialize Firebase DataBase
        database = FirebaseDatabase.getInstance()
        val completeOrderReference = database.reference.child("CompletedOrder")
            .orderByChild("currentTime")
        completeOrderReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //clear the list before population it with new data
                listOfCompleteOrderList.clear()
                for (orderSnapshot in snapshot.children) {
                    val completeOrder = orderSnapshot.getValue(OrderDetails::class.java)
                    completeOrder?.let {
                        listOfCompleteOrderList.add(it)
                    }
                }
                //reverse the list to display latest order first
                listOfCompleteOrderList.reverse()
                setDataIntoRecyclerView()

            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    private fun setDataIntoRecyclerView() {
        //Initialization list to hold customers name and payment status
        val customerName = mutableListOf<String>()
        val moneyStatus = mutableListOf<Boolean>()

        for (order in listOfCompleteOrderList) {
            order.userName?.let {
                customerName.add(it)
            }
            moneyStatus.add(order.paymentReceived)
        }

        val adapter = DeliveryAdapter(customerName, moneyStatus, this)
        binding.rvOrder.layoutManager = LinearLayoutManager(this)
        binding.rvOrder.adapter = adapter
    }
}