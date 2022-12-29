package com.cstcompany.cloudalarm

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cstcompany.cloudalarm.databinding.ActivityAlarmBinding


class AlarmActivity:AppCompatActivity() {
    lateinit var binding: ActivityAlarmBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mediaPlayer = MediaPlayer.create(this, R.raw.audiorezout_light_up_life)
        mediaPlayer!!.start()
        binding.stopAlarmButton.setOnClickListener {
            mediaPlayer.stop()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}