package org.phone.pe.models

import com.google.gson.annotations.SerializedName

class MessageDetail {
    @SerializedName("msg")
    var msg: String? = null

    @SerializedName("date")
    var date: String? = null

    @SerializedName("sender")
    var sender: String? = null

    constructor()
    constructor(msg: String?, date: String?, sender: String?) {
        this.msg = msg
        this.date = date
        this.sender = sender
    }


}