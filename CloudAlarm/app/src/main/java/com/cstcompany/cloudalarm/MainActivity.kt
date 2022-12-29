package com.cstcompany.cloudalarm

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.cstcompany.cloudalarm.data.Alarm
import com.cstcompany.cloudalarm.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


const val TAG = "MYTAG"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: AlarmRecyclerViewAdapter
    var hasPermission = true
    lateinit var manager: FragmentManager
    lateinit var alarmManager: AlarmManager
    override fun onResume() {
        super.onResume()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        manager = (this as AppCompatActivity).supportFragmentManager
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (Build.VERSION.SDK_INT >= 31) {
            hasPermission = alarmManager.canScheduleExactAlarms()
            if (!hasPermission) {
                startActivity(Intent(this, PermissionActivity::class.java))
            }
        }


        if(hasPermission){
            viewModel = ViewModelProvider(this)[MainViewModel::class.java]
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            adapter = AlarmRecyclerViewAdapter(viewModel.alarms, manager, viewModel, alarmManager)
            lifecycleScope.launch {
                viewModel.getAlarms(alarmManager)
                delay(100)
                adapter.updateAll(viewModel.alarms)
            }

            binding.alarmsRecyclerView.layoutManager = LinearLayoutManager(this)
            binding.alarmsRecyclerView.adapter = adapter

            binding.addAlarm.setOnClickListener {
                val picker =
                    MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(12)
                        .setMinute(10)
                        .setTitleText("Select alarm time")
                        .build()

                picker.show(manager, "tag")
                picker.addOnPositiveButtonClickListener{
                    val newAlarm = Alarm()
                    newAlarm.hour = picker.hour
                    newAlarm.minute = picker.minute
                    Log.d(TAG, "onCreate: ${newAlarm.id}")
                    adapter.addAlarm(newAlarm)
                }
            }
        }
    }
}