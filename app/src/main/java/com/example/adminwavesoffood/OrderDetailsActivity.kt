package com.example.adminwavesoffood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminwavesoffood.Adapter.OrderDetailsAdapter
import com.example.adminwavesoffood.Model.OrderDetails
import com.example.adminwavesoffood.databinding.ActivityOrderDetailsBinding

class OrderDetailsActivity : AppCompatActivity() {
    val binding: ActivityOrderDetailsBinding by lazy {
        ActivityOrderDetailsBinding.inflate(layoutInflater)
    }
    private var userName: String? = null
    private var phone: String? = null
    private var address: String? = null
//    private var email: String? = null

    private var totalPrice: String? = null

    private var foodName: ArrayList<String> = arrayListOf()
    private var foodImg: ArrayList<String> = arrayListOf()
    private var food_Qty: ArrayList<Int> = arrayListOf()
    private var foodPrice: ArrayList<String> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.apply {

            backBtn.setOnClickListener {
                finish()
            }
            getDataFromIntent()
        }
    }

    private fun getDataFromIntent() {
        val receivedOrderDetails = intent.getSerializableExtra("UserOrderDetails") as OrderDetails
        receivedOrderDetails?.let {
            userName = receivedOrderDetails.userName
            foodName = receivedOrderDetails.foodNames as ArrayList<String>
            foodImg = receivedOrderDetails.foodImages as ArrayList<String>
            food_Qty = receivedOrderDetails.foodQty as ArrayList<Int>
            foodPrice = receivedOrderDetails.foodPrices as ArrayList<String>
            address = receivedOrderDetails.address
            phone = receivedOrderDetails.phoneNumber
//            email = receivedOrderDetails.email
            userName = receivedOrderDetails.userName
            totalPrice = receivedOrderDetails.totalPrice

            setUserDetails()
            setAdapter()

        }


    }

    private fun setAdapter() {
        val adapter = OrderDetailsAdapter(this, foodName, foodImg, foodPrice, food_Qty)
        binding.rvOrderDetails.layoutManager = LinearLayoutManager(this)
        binding.rvOrderDetails.adapter = adapter
    }

    private fun setUserDetails() {
        binding.profileEdName.text = userName
        binding.profileEdPhoneNumber.text = phone
        binding.profileEdAddress.text = address
//        binding.profileEdEmail.text = email
        binding.profileTotalAmount.text = totalPrice


    }
}