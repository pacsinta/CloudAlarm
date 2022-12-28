package com.cstcompany.cloudalarm.data.fetch

import com.cstcompany.cloudalarm.data.Alarm

interface AlarmDatabase {
    suspend fun getAlarms(): List<Alarm>
    suspend fun getAlarm(id: Int): Alarm?
    suspend fun addAlarm(alarm: Alarm)
    suspend fun updateAlarm(alarm: Alarm)
    suspend fun deleteAlarm(alarm: Alarm)
}