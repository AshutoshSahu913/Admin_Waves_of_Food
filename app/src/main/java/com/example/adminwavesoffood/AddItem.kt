package com.example.adminwavesoffood

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.example.adminwavesoffood.Model.ItemModel
import com.example.adminwavesoffood.databinding.ActivityAddItemBinding
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.Circle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class AddItem : AppCompatActivity() {
    val binding: ActivityAddItemBinding by lazy {
        ActivityAddItemBinding.inflate(layoutInflater)
    }

    //Food Item Details
    private lateinit var foodName: String
    private lateinit var foodPrice: String
    private lateinit var foodDescription: String
    private lateinit var foodIngredient: String
    private var foodImg: Uri? = null

    // Firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //initialize firebase auth
        auth = FirebaseAuth.getInstance()

        //initialize firebase database instance
        database = FirebaseDatabase.getInstance()

        binding.addItemBtn.setOnClickListener {
//            get data form filed
            foodName = binding.inputItemName.text.toString().trim()
            foodPrice = binding.inputItemPrice.text.toString().trim()
            foodDescription = binding.inputItemDes.text.toString().trim()
            foodIngredient = binding.inputItemIngredients.text.toString().trim()
//            foodImg=binding.inputItemName.text.toString().trim()

            //set if background is fill
            binding.inputItemName.setBackgroundResource(R.drawable.edittextshape)
            binding.inputItemPrice.setBackgroundResource(R.drawable.edittextshape)
            binding.inputItemDes.setBackgroundResource(R.drawable.edittextshape)
            binding.inputItemIngredients.setBackgroundResource(R.drawable.edittextshape)
            binding.inputItemImg.setBackgroundResource(R.drawable.edittextshape)
            if (!(foodName.isBlank() || foodPrice.isBlank() || foodDescription.isBlank() || foodIngredient.isBlank())) {
                binding.addItemBtn.visibility = View.GONE
                binding.loader1.visibility = View.VISIBLE
                binding.uploadingProducts.visibility = View.VISIBLE
                loader1()
                uploadData()
                Toast.makeText(this, "Item Add Successfully", Toast.LENGTH_SHORT).show()
            } else {
                //set if background is blank
                if (foodName.isBlank()) {
                    binding.inputItemName.requestFocus()
                    binding.inputItemName.setBackgroundResource(R.drawable.edittextshape_error)
                }
                if (foodPrice.isBlank()) {
//                    binding.inputItemName.requestFocus()
                    binding.inputItemPrice.setBackgroundResource(R.drawable.edittextshape_error)
                }
                if (foodDescription.isBlank()) {
//                    binding.inputItemName.requestFocus()
                    binding.inputItemDes.setBackgroundResource(R.drawable.edittextshape_error)
                }
                if (foodIngredient.isBlank()) {
//                    binding.inputItemName.requestFocus()
                    binding.inputItemIngredients.setBackgroundResource(R.drawable.edittextshape_error)
                }
                Toast.makeText(this, "Fill All the Details", Toast.LENGTH_SHORT).show()
            }

        }

        binding.inputItemImg.setOnClickListener {
            pickImage.launch("image/*")

        }

        binding.backBtn.setOnClickListener {
            finish()
        }
    }


    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            binding.itemImg.setImageURI(uri)
            foodImg = uri
        }
    }

    private fun uploadData() {
        //get a reference to the "menu" node in the database
        val menuRef = database.getReference("Menu")

        //Genrate a unique key for the new Menu item
        val newItemKey = menuRef.push().key

        if (foodImg != null) {

            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("menu_images/{$newItemKey}.jpg")
            val uploadTask = imageRef.putFile(foodImg!!)

            uploadTask.addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                    //Create a new Menu
                    val newItem = ItemModel(
                        key = newItemKey,
                        foodName = foodName,
                        foodPrice = foodPrice,
                        foodDes = foodDescription,
                        foodIngredients = foodIngredient,
                        foodImg = downloadUrl.toString()
                    )
                    newItemKey?.let { key ->
                        menuRef.child(key).setValue(newItem).addOnSuccessListener {
                            binding.uploadingProducts.visibility = View.GONE
                            binding.loader1.visibility = View.GONE
                            Toast.makeText(this, "Data Upload Successfully", Toast.LENGTH_SHORT)
                                .show()
                            finish()
                        }
                            .addOnFailureListener {
                                binding.addItemBtn.visibility = View.VISIBLE
                                binding.uploadingProducts.visibility = View.GONE
                                Toast.makeText(this, "Data Upload Failed", Toast.LENGTH_SHORT)
                                    .show()
                            }
                    }
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Data Upload Failed", Toast.LENGTH_SHORT).show()
            }
        } else {
            binding.inputItemImg.setBackgroundResource(R.drawable.edittextshape_error)
            Toast.makeText(this, "Please Select an Image", Toast.LENGTH_SHORT).show()
        }

    }

    fun loader1() {
        // code for loader
        val progressBar = binding.loader1 as ProgressBar
        val circle: Sprite = Circle()
        progressBar.indeterminateDrawable = circle
    }
}