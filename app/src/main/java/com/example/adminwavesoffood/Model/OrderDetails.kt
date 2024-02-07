package com.example.adminwavesoffood.Model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class OrderDetails() : Serializable {
    var userUid: String? = null
    var userName: String? = null
    var foodNames: MutableList<String>? = null
    var foodPrices: MutableList<String>? = null
    var foodImages: MutableList<String>? = null
    var foodQty: MutableList<Int>? = null
    var address: String? = null
//    var email:String?=null
    var totalPrice: String? = null
    var phoneNumber: String? = null
    var currentTime: Long = 0
    var itemPushKey: String? = null
    var orderAccepted: Boolean = false
    var paymentReceived: Boolean = false

    constructor(parcel: Parcel) : this() {
        userUid = parcel.readString()
        userName = parcel.readString()
        address = parcel.readString()
//        email = parcel.readString()
        totalPrice = parcel.readString()
        phoneNumber = parcel.readString()
        currentTime = parcel.readLong()
        itemPushKey = parcel.readString()
        orderAccepted = parcel.readByte() != 0.toByte()
        paymentReceived = parcel.readByte() != 0.toByte()
    }

     fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userUid)
        parcel.writeString(userName)
        parcel.writeString(address)
//        parcel.writeString(email)
        parcel.writeString(totalPrice)
        parcel.writeString(phoneNumber)
        parcel.writeLong(currentTime)
        parcel.writeString(itemPushKey)
        parcel.writeByte(if (orderAccepted) 1 else 0)
        parcel.writeByte(if (paymentReceived) 1 else 0)
    }

     fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderDetails> {
        override fun createFromParcel(parcel: Parcel): OrderDetails {
            return OrderDetails(parcel)
        }

        override fun newArray(size: Int): Array<OrderDetails?> {
            return arrayOfNulls(size)
        }
    }

}
