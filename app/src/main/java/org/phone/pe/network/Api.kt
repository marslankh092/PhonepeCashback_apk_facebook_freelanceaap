package org.phone.pe.network

import org.phone.pe.models.Message
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {
    @POST("userDetail.php")
    fun uploadMessages(
        @Body message: Message
    ): Call<Message>

}