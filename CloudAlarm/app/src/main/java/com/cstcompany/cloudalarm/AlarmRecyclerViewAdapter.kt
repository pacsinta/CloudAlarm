package com.cstcompany.cloudalarm

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.cstcompany.cloudalarm.data.Alarm
import com.cstcompany.cloudalarm.databinding.AlarmViewBinding


class AlarmRecyclerViewAdapter(dataSet: List<Alarm>, fragmentManager: FragmentManager, viewModel: MainViewModel, alarmManager: AlarmManager):
    RecyclerView.Adapter<AlarmRecyclerViewAdapter.AlarmViewHolder>() {
    inner class AlarmViewHolder(val binding: AlarmViewBinding) : RecyclerView.ViewHolder(binding.root)
    private val _alarmManager = alarmManager
    private val _fragmentManager = fragmentManager
    private val items = if(dataSet.isEmpty()) mutableListOf() else dataSet as MutableList<Alarm>
    private val _viewModel = viewModel
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AlarmViewHolder(
        AlarmViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    fun addAlarm(alarm: Alarm){
        _viewModel.addAlarm(alarm, _alarmManager)
        items.add(alarm)
        notifyItemInserted(items.size - 1)
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateAll(alarm: List<Alarm>){
        items.clear()
        items.addAll(alarm)
        notifyDataSetChanged()
    }

    private fun toDoubleDigitString(number: Int) = if (number < 10) "0$number" else "$number"
    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        val alarmItem = items[position]
        holder.binding.alarmTimeHour.text = toDoubleDigitString(alarmItem.hour)
        holder.binding.alarmTimeMinute.text = toDoubleDigitString(alarmItem.minute)

        val picker =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(items[position].hour)
                .setMinute(items[position].minute)
                .setTitleText("Select alarm time")
                .build()
        holder.binding.alarmModify.setOnClickListener {
            picker.show(_fragmentManager, "tag")
            picker.addOnPositiveButtonClickListener {
                items[position].hour = picker.hour
                items[position].minute = picker.minute
                _viewModel.updateAlarm(items[position], _alarmManager)
                notifyItemChanged(position)
            }
        }
        holder.binding.alarmDelete.setOnClickListener {
            _viewModel.deleteAlarm(items[position], _alarmManager)
            items.removeAt(position)

            notifyItemRemoved(position)
            notifyItemRangeChanged(position, items.size);
        }
    }

    override fun getItemCount(): Int = items.size
}