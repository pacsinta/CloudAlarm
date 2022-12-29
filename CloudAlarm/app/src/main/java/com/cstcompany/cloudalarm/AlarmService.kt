package com.cstcompany.cloudalarm

import android.app.Service
import android.content.Intent
import android.os.IBinder

class AlarmService:Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        val alarmIntent = Intent(this, AlarmActivity::class.java)
        alarmIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(alarmIntent)
    }
}