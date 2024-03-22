package com.example.wheatherapp.ui.alert.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.wheatherapp.MainActivity
import com.example.wheatherapp.R

private const val CHANNEL_ID = 1
private const val FOREGROUND_ID = 2

class AlertService : Service() {

    private var notificationManager: NotificationManager? = null
    var alertWindowManger: AlertWindowManager? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
         super.onStartCommand(intent, flags, startId)

        val description = intent?.getStringExtra("description")

        notificationChannel()
        startForeground(FOREGROUND_ID,makeNotification(description!!))

        if (Settings.canDrawOverlays(this)){
            alertWindowManger = AlertWindowManager(this , description)
            alertWindowManger!!.initializeWindowManager()
        }
           return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    private fun makeNotification(description: String): Notification {
        val intent = Intent(applicationContext , MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val sound =
            Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + this.packageName + "/" + R.raw.oh_no)

        return NotificationCompat.Builder(
            applicationContext, "$CHANNEL_ID"
        )
            .setSmallIcon(R.drawable.notifcation)
            .setContentText(description)
            .setContentTitle("Weather Alarm")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(description)
            )
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setLights(Color.RED, 3000, 3000)
            .setSound(sound)
            .setAutoCancel(true)
            .setContentIntent(
                PendingIntent.getActivity(
                    this,
                    0,
                    Intent(
                        this,
                        MainActivity::class.java
                    ).apply { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) },
                    PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
                )
            )
            .build()
    }

    private fun notificationChannel() {

        val sound =
            Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + this.packageName + "/" + R.raw.oh_no)
            val attributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: String = "Weather App Notification"
            val description = "Alert Notification"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(
                "$CHANNEL_ID",
                name, importance
            )

            channel.enableVibration(true)
            channel.description = description
            channel.setSound(sound,attributes)
            notificationManager = this.getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }
}