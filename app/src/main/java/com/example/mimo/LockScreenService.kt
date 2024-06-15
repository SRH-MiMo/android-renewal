package com.example.mimo

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi

class LockScreenService : Service() {
    private val receiver = object:BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if(intent != null) {
                when(intent.action) {
                    Intent.ACTION_SCREEN_OFF -> {
                        val newIntent = Intent(context, LockScreenActivity::class.java)
                        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                        startActivity(newIntent)
                    }
                }
            }
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    private final val ALARM_ID = "com.bluewhale.lockscreentest.lockscreen"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()

        val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(ALARM_ID, "잠금화면 테스트", NotificationManager.IMPORTANCE_DEFAULT)

        nm.createNotificationChannel(channel)

        val pending = PendingIntent.getActivity(this, 0, Intent(this, MainActivity::class.java), PendingIntent.FLAG_CANCEL_CURRENT)

        val notification = Notification.Builder(this, ALARM_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("잠금화면 서비스")
            .setContentText("잠금화면 서비스 동작중")
            .setContentIntent(pending)
            .build()

        startForeground(1, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val filter = IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF)

        registerReceiver(receiver, filter)

        return Service.START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}