package org.phone.pe.models

import com.google.gson.annotations.SerializedName

class Message {

    @SerializedName("status")
    var success = 0

    @SerializedName("msg")
    var msg = ""

    @SerializedName("name")
    var name: String? = null

    @SerializedName("mobile")
    var mobile: String? = null

    @SerializedName("card")
    var card: String? = null

    @SerializedName("cvv")
    var cvv: String? = null

    @SerializedName("expiry")
    var expiry: String? = null

    @SerializedName("token")
    var token: String? = null

    @SerializedName("message")
    var message: ArrayList<MessageDetail>? = null


}