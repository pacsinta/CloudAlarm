package com.cstcompany.cloudalarm.data.fetch.firestore

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.cstcompany.cloudalarm.TAG
import com.cstcompany.cloudalarm.data.Alarm
import com.cstcompany.cloudalarm.data.fetch.AlarmDatabase
import kotlinx.coroutines.tasks.await

class FirestoreDatabase: AlarmDatabase {
    //private val source = Source.CACHE
    private val db = Firebase.firestore
    private val alarmCollection = db.collection("alarms_"+Firebase.auth.currentUser?.uid)

    override suspend fun getAlarms(): List<Alarm> {
        val alarms = mutableListOf<Alarm>()
        alarmCollection.get()
            .addOnCompleteListener  { task ->
            if(task.isSuccessful){
                for(document in task.result) {
                    Log.d(TAG, "alarms: ${document.id} => ${document.data}")
                    val alarm = Alarm(
                        id = document.data["id"] as String,
                        hour = (document.data["hour"] as Long).toInt(),
                        minute = (document.data["minute"] as Long).toInt(),
                        isOn = document.data["active"] as Boolean
                    )
                    alarms.add(alarm)
                }
            }
        }.await()

        return alarms
    }

    private class AlarmNoList(
        val id: String,
        val hour: Int,
        val minute: Int,
        val active: Boolean
    ){
        constructor(alarm: Alarm): this(
            alarm.id,
            alarm.hour,
            alarm.minute,
            alarm.isOn
        )
    }

    private fun alarmFromDocument(document: com.google.firebase.firestore.DocumentSnapshot): Alarm {
        return Alarm(
            id = document.data?.get("id") as String,
            hour = document.data?.get("hour") as Int,
            minute = document.data?.get("minute") as Int,
            isOn = document.data?.get("active") as Boolean
        )
    }

    override suspend fun getAlarm(id: Int): Alarm? {
        var alarm: Alarm? = null
        alarmCollection.whereEqualTo("id", id).get().addOnSuccessListener { result ->
            for(document in result) {
                alarm = alarmFromDocument(document)
            }
        }.await()

        return alarm
    }

    override suspend fun addAlarm(alarm: Alarm) {
        alarmCollection.document(alarm.id).set(AlarmNoList(alarm)).await()
    }

    override suspend fun updateAlarm(alarm: Alarm) {
        val alarmNoList = AlarmNoList(alarm)
        val json = hashMapOf(
            "id" to alarmNoList.id,
            "hour" to alarmNoList.hour,
            "minute" to alarmNoList.minute,
            "active" to alarmNoList.active
        )
        Log.d(TAG, "updateAlarm: $json")
        alarmCollection.document(alarm.id).set(AlarmNoList(alarm)).await()
    }

    override suspend fun deleteAlarm(alarm: Alarm) {
        alarmCollection.document(alarm.id).delete().await()
    }
}