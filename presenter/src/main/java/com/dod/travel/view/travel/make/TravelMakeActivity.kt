package com.dod.travel.view.travel.make

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.dod.travel.R
import com.dod.travel.databinding.ActivityTravelMakeBinding
import com.dod.travel.util.CalenderUtil
import com.dod.travel.view.travel.make.invite.TravelInviteActivity
import java.text.SimpleDateFormat
import java.util.*

class TravelMakeActivity : AppCompatActivity() {

    private val binding by lazy { ActivityTravelMakeBinding.inflate(layoutInflater) }

    private lateinit var groupName: String

    private lateinit var selectedUserList: LongArray

    private var groupId = 0L
    private var isStartDate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        groupId = intent.getLongExtra("groupId", 0L)
        groupName = intent.getStringExtra("groupName")!!

        initView()
    }

    private fun initView() {
        binding.title.text = groupName

        binding.joinUserAdd.setOnClickListener {
            val intent = Intent(this, TravelInviteActivity::class.java)
            intent.putExtra("groupId", groupId)
            intent.putExtra("groupName", groupName)
            inviteLauncher.launch(intent)
        }

        binding.startDateAdd.setOnClickListener(dateOnClick)
        binding.startTimeAdd.setOnClickListener(dateOnClick)
        binding.endDateAdd.setOnClickListener(dateOnClick)
        binding.endTimeAdd.setOnClickListener(dateOnClick)
    }

    @SuppressLint("SetTextI18n")
    private val inviteLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            selectedUserList = result.data!!.getLongArrayExtra("idxList")!!
            binding.joinUserText.text = "참여인원 : ${selectedUserList.size+1}명"
        }
    }

    private val dateOnClick = OnClickListener {
        when(it.id) {
            R.id.start_date_add -> {
                isStartDate = true
                datePrickerShow()
            }
            R.id.start_time_add -> {
                isStartDate = true
                if(binding.startDateEdit.text.toString().isEmpty()){
                    Toast.makeText(this, "시작일 날짜를 먼저 선택해 주세요.", Toast.LENGTH_SHORT).show()
                }else {
                    timePickerShow()
                }
            }
            R.id.end_date_add -> {
                isStartDate = false
                if(binding.startDateEdit.text.toString().isEmpty()){
                    Toast.makeText(this, "시작일 날짜를 먼저 선택해 주세요.", Toast.LENGTH_SHORT).show()
                }else {
                    datePrickerShow()
                }
            }
            R.id.end_time_add -> {
                isStartDate = false
                if(binding.endDateEdit.text.toString().isEmpty()){
                    Toast.makeText(this, "종료일 날짜를 먼저 선택해 주세요.", Toast.LENGTH_SHORT).show()
                }else if(binding.startDateEdit.text.toString().length <= 10) {
                    Toast.makeText(this, "시작일 시간을 먼저 선택해 주세요.", Toast.LENGTH_SHORT).show()
                }else {
                    timePickerShow()
                }
            }
        }
    }
    
    private fun datePrickerShow(){
        val dialog = DatePickerDialog(this, dateListener,
            CalenderUtil.year(), CalenderUtil.month(), CalenderUtil.day())
        dialog.show()
    }
    
    @SuppressLint("SimpleDateFormat")
    private val dateListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val dateStr = "$year-${addZero(month+1)}-${addZero(day)}"
        val nowDate = Date()
        val selectDate = formatter.parse("$dateStr 24:00:00")

        if(compareDate(selectDate as Date, nowDate)) {
            if(isStartDate){
                binding.startDateEdit.text = dateStr
            }else {
                var startDateStr = binding.startDateEdit.text.toString()
                startDateStr = if(startDateStr.length <= 10) {
                    "$startDateStr 00:00:00"
                }else {
                    val arr = startDateStr.split("\n")
                    arr[0] + " " + arr[1]
                }

                val startDate = formatter.parse(startDateStr)
                if(compareDate(selectDate, startDate as Date)) {
                    binding.endDateEdit.text = dateStr
                }
            }
        }
    }

    private fun timePickerShow(){
        val dialog = TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, timeListener,
            CalenderUtil.hour(), CalenderUtil.min(), true)
        dialog.show()
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private val timeListener = OnTimeSetListener { _, hourOfDay, minute ->
        val timeStr = "\n${addZero(hourOfDay)}:${addZero(minute)}:00"
        if(isStartDate) {
            binding.startDateEdit.text = binding.startDateEdit.text.toString().substring(0, 10) + timeStr
        }else {
            var startDateStr = binding.startDateEdit.text.toString()
            startDateStr = if(startDateStr.length <= 10) {
                "$startDateStr 00:00:00"
            }else {
                val arr = startDateStr.split("\n")
                arr[0] + " " + arr[1]
            }

            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val startDate = formatter.parse(startDateStr)
            val endDate = formatter.parse(binding.endDateEdit.text.toString() + " ${addZero(hourOfDay)}:${addZero(minute)}:00")

            if(compareDate(endDate as Date, startDate as Date)){
                binding.endDateEdit.text = binding.endDateEdit.text.toString().substring(0, 10) + timeStr
            }
        }
    }

    private fun compareDate(selectDate: Date, compareDate: Date): Boolean {
        val result = selectDate.compareTo(compareDate)

        return if(result == 0) {
            true
        }else if(result < 0){
            Toast.makeText(this, "더 뒤의 날짜 혹은 시간을 선택해야 합니다.", Toast.LENGTH_SHORT).show()
            false
        }else {
            true
        }
    }

    private fun addZero(num: Int): String {
        return if(num.toString().length == 1) {
            "0$num"
        }else {
            num.toString()
        }
    }
}