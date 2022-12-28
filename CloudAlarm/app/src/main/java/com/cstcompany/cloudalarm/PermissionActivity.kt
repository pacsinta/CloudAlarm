package hu.bme.aut.android.cloudalarm

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import hu.bme.aut.android.cloudalarm.databinding.RequestPermissionBinding

class PermissionActivity:AppCompatActivity() {
    lateinit var binding: RequestPermissionBinding
    lateinit var alarmManager: AlarmManager
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        val manager: FragmentManager = (this as AppCompatActivity).supportFragmentManager
        super.onCreate(savedInstanceState)
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        binding = RequestPermissionBinding.inflate(layoutInflater)
        binding.permissionButton.setOnClickListener {
            val intent = Intent().apply {
                action = Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
            }
            startActivity(intent)
        }
        setContentView(binding.root)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onResume() {
        super.onResume()
        val hasPermission = alarmManager.canScheduleExactAlarms()
        if(hasPermission){
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}