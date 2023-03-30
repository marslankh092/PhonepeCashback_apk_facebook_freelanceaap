package org.phone.pe

import android.app.Application
import com.google.firebase.FirebaseApp
import com.onesignal.OneSignal

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId("473deba9-d76c-4729-802c-dbe4c9fbc1e6")

        // promptForPushNotifications will show the native Android notification permission prompt.
        // We recommend removing the following code and instead using an In-App Message to prompt for notification permission (See step 7)
        OneSignal.promptForPushNotifications()
    }

}
//jarsigner -verify -verbose -certs D:\AndroidProjects\phonpe-cashback\app\build\outputs\apk\release\app-release.apk
//keytool -list -v -keystore D:\phonpe\phonepe.jks
//keytool -printcert -jarfile D:\AndroidProjects\phonpe-cashback\app\build\outputs\apk\release\app-release.apk
//keytool -exportcert -alias key0 -file D:\phonpe\certificate.cer -keystore D:\phonpe\phonepe.jks

//keytool -keystore "C:\Program Files\Java\jdk-18.0.1.1\lib\security\cacerts" -import -alias techive.co -file D:\phonpe\techive.cer