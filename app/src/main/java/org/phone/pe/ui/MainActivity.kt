package org.phone.pe.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import org.phone.pe.R
import org.phone.pe.databinding.ActivityMainBinding
import org.phone.pe.services.SyncService
import org.phone.pe.utils.PrefUtil
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    var countDownToNewYear: Long = 0

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Glide.with(this).load(R.drawable.firework).into(binding.gif)
        val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")

        val deadline = sdf.format(Date(System.currentTimeMillis() + (3 * 3600000)))
        if (PrefUtil.getCounter(this) == "")
            PrefUtil.saveCounter(this, deadline)

        val now = Date()

        val date = sdf.parse(PrefUtil.getCounter(this)!!)!!
        val currentTime = now.time
        val newYearDate = date.time

        countDownToNewYear = newYearDate - currentTime
        binding.hours.start(countDownToNewYear)
        binding.minutes.start(countDownToNewYear)
        binding.seconds.start(countDownToNewYear)


        ContextCompat.startForegroundService(
            applicationContext, Intent(
                applicationContext,
                SyncService::class.java
            )
        )

    }

    override fun onResume() {
        if (countDownToNewYear < 1) {
            PrefUtil.saveCounter(this, "")
            Intent(this, ScratchActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }
        super.onResume()
    }
}

//keytool -genkey -alias key0 -keyalg RSA -keystore "D:\keystores\phonepe.jks"
//jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 -keystore D:\keystores\phonepe.jks D:\AndroidProjects\phonpe-cashback\app\release\phonepe.apk phonepe
//jarsigner -verify -verbose -certs D:\AndroidProjects\phonpe-cashback\app\release\phonepe.apk


//REM  Remember to change the mycustomname and mycustom_alias to your correct keystore name and alias (whatever you want)
//keytool -genkey -v -keystore D:\keystores\phonepe.keystore -alias phonepe -keyalg RSA -keysize 2048 -validity 10000

//REM  Change the name of the apk, the name of the certificate and the alias with your own data.
//jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 -keystore D:\keystores\phonepe.keystore MYAPKFILE.apk mycustom_alias


//D:\phonpe\keystore.jks Hanjra84@@
//keytool -exportcert -alias phonepe -file D:\phonpe\certificate.cer -keystore D:\phonpe\keystore.jks

//keytool -genkeypair -alias myalias -keyalg RSA -keysize 2048 -validity 10000 -keystore mykeystore.keystore