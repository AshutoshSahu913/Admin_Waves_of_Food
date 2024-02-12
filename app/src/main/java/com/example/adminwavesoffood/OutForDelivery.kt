package com.example.adminwavesoffood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminwavesoffood.Adapter.DeliveryAdapter
import com.example.adminwavesoffood.Model.OrderDetails
import com.example.adminwavesoffood.databinding.ActivityOutForDeliveryBinding
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.Circle
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
        loader()
        binding.loader2.visibility = View.VISIBLE
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

        if (listOfCompleteOrderList.isEmpty()) {
            binding.emptyTxt.visibility = View.VISIBLE
            binding.loader2.visibility = View.GONE
        } else {
            binding.loader2.visibility = View.GONE
            binding.emptyTxt.visibility = View.GONE
            val adapter = DeliveryAdapter(customerName, moneyStatus, this)
            binding.rvOrder.layoutManager = LinearLayoutManager(this)
            binding.rvOrder.adapter = adapter
        }
    }

    fun loader() {
        // code for loader
        val progressBar = binding.loader2 as ProgressBar
        val circle: Sprite = Circle()
        progressBar.indeterminateDrawable = circle
    }

}