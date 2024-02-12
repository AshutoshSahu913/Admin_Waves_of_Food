package com.example.adminwavesoffood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminwavesoffood.Adapter.MenuItemAdapter
import com.example.adminwavesoffood.Model.ItemModel
import com.example.adminwavesoffood.databinding.ActivityAllItemBinding
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.Circle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AllItemActivity : AppCompatActivity() {
    private lateinit var databaseReferences: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private var menuItems: ArrayList<ItemModel> = ArrayList()


    val binding: ActivityAllItemBinding by lazy {
        ActivityAllItemBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //initialize variable
        databaseReferences = FirebaseDatabase.getInstance().reference
        binding.loader2.visibility = View.VISIBLE
        loader2()
        retrieveMenuItem()

        binding.backBtn.setOnClickListener {
            finish()
        }

    }

    private fun retrieveMenuItem() {
//        var userId=FirebaseAuth.getInstance().currentUser?.uid
        database = FirebaseDatabase.getInstance()
        val foodRef: DatabaseReference = database.reference.child("Menu")

        //fetch data from data base
        foodRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //Clear existing data before populating
                menuItems.clear()

                //loop for through each food item
                for (foodSnapshot in snapshot.children) {
                    val menuItem = foodSnapshot.getValue(ItemModel::class.java)
                    menuItem?.let {
                        menuItems.add(it)
                    }
                }
//                Log.d("MenuItems", "Size: ${menuItems.size}")
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
//                Log.d("DatabaseError", "Error: {${error.message}}")

            }
        })
    }

    private fun setAdapter() {
        val adapter =
            MenuItemAdapter(menuItems, this@AllItemActivity, databaseReferences) { position ->
                deleteMenuItem(position)
            }
        binding.loader2.visibility = View.GONE
        binding.rvItemList.layoutManager = LinearLayoutManager(this)
        binding.rvItemList.adapter = adapter
    }

    private fun deleteMenuItem(position: Int) {
        val menuItemToDelete = menuItems[position]
        val menuItemKey = menuItemToDelete.key
        var foodMenuReference = database.reference.child("Menu").child(menuItemKey!!)
        foodMenuReference.removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                menuItems.removeAt(position)
                binding.rvItemList.adapter?.notifyItemRemoved(position)
            } else {
                Toast.makeText(this, "Item not Deleted!", Toast.LENGTH_SHORT).show()

            }
        }
    }

    fun loader2() {
        // code for loader
        val progressBar = binding.loader2 as ProgressBar
        val circle: Sprite = Circle()
        progressBar.indeterminateDrawable = circle
    }
}