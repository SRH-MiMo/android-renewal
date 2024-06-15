package com.example.mimo

import android.app.ActivityManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes

object LockReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when(intent.action) {
            Intent.ACTION_SCREEN_ON -> {
                navigateToLock(context)
            }
        }
    }

    private fun navigateToLock(context: Context) {
        context.startActivity(
            Intent(context, LockScreenActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
            }
        )
    }
}

class LockService : Service() {

    private lateinit var lockServiceManager: LockServiceManager

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        startForeground(SERVICE_ID, createNotificationBuilder())
        startLockReceiver()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        stopLockReceiver()
        lockServiceManager.stop()
        super.onDestroy()
    }

    private fun startLockReceiver() {
        val intentFilter = IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_ON)
            addAction(Intent.ACTION_SCREEN_OFF)
        }
        registerReceiver(LockReceiver, intentFilter)
    }

    private fun stopLockReceiver() {
        unregisterReceiver(LockReceiver)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val notificationChannel = SimpleNotificationBuilder.createChannel(
            LOCK_CHANNEL,
            getStringWithContext(R.string.app_name),
            NotificationManager.IMPORTANCE_HIGH,
            getStringWithContext(R.string.lock_screen_description)
        )

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(notificationChannel)
    }

    private fun getStringWithContext(
        @StringRes stringRes: Int
    ): String {
        return applicationContext.getString(stringRes)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationBuilder(): Notification {
        return SimpleNotificationBuilder.createBuilder(
            context = this,
            channelId = LOCK_CHANNEL,
            title = getStringWithContext(R.string.app_name),
            text = getStringWithContext(R.string.lock_screen_description),
            icon = R.drawable.ic_launcher_foreground,
        )
    }

    private companion object {
        const val LOCK_CHANNEL = "LOCK_CHANNEL"
        const val SERVICE_ID: Int = 1
    }
}

object SimpleNotificationBuilder {
    @RequiresApi(Build.VERSION_CODES.O)
    fun createChannel(
        channelId: String,
        name: String,
        importance: Int = NotificationManager.IMPORTANCE_HIGH,
        description: String
    ) =
        NotificationChannel(channelId, name, importance).apply {
            setShowBadge(false)
            enableLights(true)
            this.description = description
            lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            lightColor = Color.BLACK
        }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createBuilder(
        context: Context,
        channelId: String,
        title: String,
        text: String,
        @DrawableRes icon: Int,
    ) = Notification.Builder(context, channelId).apply {
        setOngoing(true)
        setShowWhen(true)
        setSmallIcon(icon)
        setContentTitle(title)
        setContentText(text)
    }.build()
}




fun Context.isServiceRunning(serviceClass: Class<*>): Boolean {
    val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

    for (service in activityManager.getRunningServices(Integer.MAX_VALUE)) {
        if (serviceClass.name == service.service.className) {
            Log.e("isServiceRunning", "Service is running")
            return true
        }
    }
    return false
}

abstract class BaseForegroundServiceManager<T : Service>(
    val context: Context,
    val targetClass: Class<T>,
) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun start() = synchronized(this) {
        val intent = Intent(context, targetClass)

        if (!context.isServiceRunning(targetClass)) {
            context.startForegroundService(intent)
        }
    }

    fun stop() = synchronized(this) {
        val intent = Intent(context, targetClass)

        if (context.isServiceRunning(targetClass)) {
            context.stopService(intent)
        }
    }
}

class LockServiceManager constructor(
    val applicationContext: Context
): BaseForegroundServiceManager<LockService>(
    context = applicationContext,
    targetClass = LockService::class.java,
)

