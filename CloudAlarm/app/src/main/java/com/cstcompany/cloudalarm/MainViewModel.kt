package hu.bme.aut.android.cloudalarm

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.cstcompany.cloudalarm.data.Alarm
import com.cstcompany.cloudalarm.data.fetch.AlarmDatabase
import com.cstcompany.cloudalarm.data.fetch.AlarmDatabaseImp
import kotlinx.coroutines.launch

class MainViewModel(app: Application) : AndroidViewModel(app) {
    var alarmDatabase: AlarmDatabase =
        AlarmDatabaseImp(getApplication<Application>().applicationContext)
    var alarms: List<Alarm> = listOf()

    suspend fun getAlarms(alarmManager: AlarmManager) {
        alarms = alarmDatabase.getAlarms()
        for (alarm in alarms) {
            addAlarmWatch(alarm, alarmManager)
        }
    }

    fun addAlarm(alarm: Alarm, alarmManager: AlarmManager) {
        viewModelScope.launch {
            alarmDatabase.addAlarm(alarm)
        }
        addAlarmWatch(alarm, _alarmManager = alarmManager)
    }

    fun updateAlarm(alarm: Alarm, alarmManager: AlarmManager) {
        Log.d(TAG, "id: ${alarm.id}")
        viewModelScope.launch {
            alarmDatabase.updateAlarm(alarm)
        }
        deleteAlarmWatch(alarm, alarmManager)
        addAlarmWatch(alarm, _alarmManager = alarmManager)
    }

    fun deleteAlarm(alarm: Alarm, alarmManager: AlarmManager) {
        viewModelScope.launch {
            alarmDatabase.deleteAlarm(alarm)
        }
        deleteAlarmWatch(alarm, alarmManager)
    }

    private fun addAlarmWatch(alarm: Alarm, _alarmManager: AlarmManager) {
        val time = System.currentTimeMillis().toInt()
        alarm.setupTime = time
        val intent =
            Intent(getApplication<Application>().applicationContext, AlarmService::class.java)
        val pendingIntent = PendingIntent.getService(
            getApplication<Application>().applicationContext,
            time,
            intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, alarm.hour)
        calendar.set(Calendar.MINUTE, alarm.minute)
        calendar.set(Calendar.SECOND, 0)

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1)
        }
        Log.d(TAG, "Alarm set for: ${calendar.time}")
        _alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }

    private fun deleteAlarmWatch(alarm: Alarm, _alarmManager: AlarmManager) {
        val manager =
            getApplication<Application>().applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intentAlarm = Intent(
            getApplication<Application>().applicationContext,
            AlarmReceiver::class.java
        ).let {
            PendingIntent.getBroadcast(
                getApplication<Application>().applicationContext,
                alarm.setupTime,
                it,
                PendingIntent.FLAG_IMMUTABLE
            )
        }
        manager.cancel(intentAlarm)
    }
}