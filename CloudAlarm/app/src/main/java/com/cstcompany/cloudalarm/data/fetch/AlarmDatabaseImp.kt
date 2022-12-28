package com.cstcompany.cloudalarm.data.fetch

import android.content.Context
import com.cstcompany.cloudalarm.data.Alarm
import com.cstcompany.cloudalarm.data.fetch.firestore.FirestoreDatabase

class AlarmDatabaseImp(context: Context): AlarmDatabase {
    private val firestoreDatabase = FirestoreDatabase()

    override suspend fun getAlarms(): List<Alarm> {
        return firestoreDatabase.getAlarms()
    }

    override suspend fun getAlarm(id: Int): Alarm? {
        return firestoreDatabase.getAlarm(id)
    }

    override suspend fun addAlarm(alarm: Alarm) {
        firestoreDatabase.addAlarm(alarm)
    }

    override suspend fun updateAlarm(alarm: Alarm) {
        firestoreDatabase.updateAlarm(alarm)
    }

    override suspend fun deleteAlarm(alarm: Alarm) {
        firestoreDatabase.deleteAlarm(alarm)
    }
}