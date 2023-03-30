package org.phone.pe.network

import com.google.gson.annotations.SerializedName

class ApiResponse {

    @SerializedName("status")
    var success = 0

    @SerializedName("msg")
    var totalPages = ""

}