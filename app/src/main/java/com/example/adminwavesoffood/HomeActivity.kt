package com.example.adminwavesoffood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.adminwavesoffood.Model.OrderDetails
import com.example.adminwavesoffood.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeActivity : AppCompatActivity() {
    val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var completedOrderReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth=FirebaseAuth.getInstance()

        binding.apply {
            addMenu.setOnClickListener {
                startActivity(Intent(this@HomeActivity, AddItem::class.java))
            }
            addMenuItem.setOnClickListener {
                startActivity(Intent(this@HomeActivity, AllItemActivity::class.java))
            }
            orderDispatch.setOnClickListener {
                startActivity(Intent(this@HomeActivity, OutForDelivery::class.java))
            }

            addNewUser.setOnClickListener {
                startActivity(Intent(this@HomeActivity, AddNewUser::class.java))
            }
            profile.setOnClickListener {
                startActivity(Intent(this@HomeActivity, AdminProfile::class.java))
            }
            logout.setOnClickListener {
                auth.signOut()
                startActivity(Intent(this@HomeActivity, RegisterActivity::class.java))
            }
            pendingOrderBtn.setOnClickListener {
                val intent = Intent(this@HomeActivity, PendingOrders::class.java)
                startActivity(intent)
            }
            pendingOrder()
            completedOrder()
            wholeTimeEarning()

        }
    }

    private fun wholeTimeEarning() {
        val listOfTotalPay = mutableListOf<Int>()
        val completedOrderReference1 =
            FirebaseDatabase.getInstance().reference.child("CompletedOrder")

        completedOrderReference1.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (orderSnapshot in snapshot.children) {
                    val completeOrder = orderSnapshot.getValue(OrderDetails::class.java)
                    completeOrder?.totalPrice?.replace("₹", "")?.toIntOrNull()
                        ?.let { i ->
                            listOfTotalPay.add(i)
                        }
                }
                binding.wholeTimeEarning.text = listOfTotalPay.sum().toString() + " ₹"

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })


    }

    private fun completedOrder() {
        database = FirebaseDatabase.getInstance()
        val completedOrderReference = database.reference.child("CompletedOrder")
        var completeOrderItemCount = 0
        completedOrderReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                completeOrderItemCount = snapshot.childrenCount.toInt()
                binding.completeOrderCount.text = completeOrderItemCount.toString()

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun pendingOrder() {
        database = FirebaseDatabase.getInstance()
        val pendingOrderReference = database.reference.child("OrderDetails")
        var pendingOrderItemCount = 0
        pendingOrderReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                pendingOrderItemCount = snapshot.childrenCount.toInt()
                binding.pendingOrder.text = pendingOrderItemCount.toString()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })


    }


}