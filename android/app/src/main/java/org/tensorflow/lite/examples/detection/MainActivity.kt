package org.tensorflow.lite.examples.detection

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import org.tensorflow.lite.examples.detection.Record.RecordActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var tv_currdate: TextView
    private lateinit var tv_currdate_num: TextView
    private lateinit var cl_camera: ConstraintLayout
    private lateinit var cl_record: ConstraintLayout

    private val sdf = SimpleDateFormat("yyyy.MM.dd")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        tv_currdate = findViewById(R.id.tv_currdate)
        tv_currdate_num = findViewById(R.id.tv_currdate_num)
        cl_camera = findViewById(R.id.cl_camera)
        cl_record = findViewById(R.id.cl_record)
        addListener()
    }

    @SuppressLint("SetTextI18n")
    private fun addListener() {
        tv_currdate.setOnClickListener {
            val listener =
                DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, month: Int, day: Int ->
                    tv_currdate.text = "${year}년 ${month + 1}월 ${day}일 부터 오늘까지"
                    val today = Calendar.getInstance()
                    val todayYear = today.get(Calendar.YEAR)
                    val todayMonth = today.get(Calendar.MONTH)
                    val todayDay = today.get(Calendar.DATE)

                    val initTime = sdf.parse(gerStringTimeFormat(year, month, day))
                    val todayTime = sdf.parse(gerStringTimeFormat(todayYear, todayMonth, todayDay + 1))
                    val diffTime = todayTime.time - initTime.time
                    val calDateDays = diffTime / (24 * 60 * 60 * 1000)
                    tv_currdate_num.text = "${if(calDateDays < 10) "0${calDateDays}" else calDateDays.toString()}일차"

                }
            val splitList = tv_currdate.text.toString().split(" ")
            val year = splitList[0].substring(0, splitList[0].lastIndex).toInt()
            val month = splitList[1].substring(0, splitList[1].lastIndex).toInt()
            val day = splitList[2].substring(0, splitList[2].lastIndex).toInt()
            val dialog = DatePickerDialog(this, listener, year, month - 1, day)
            dialog.show()
        }
        cl_camera.setOnClickListener {
            val intent = Intent(this, DetectorActivity::class.java)
            startActivity(intent)
        }
        cl_record.setOnClickListener {
            val intent = Intent(this, RecordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun gerStringTimeFormat(year: Int, month: Int, day: Int): String {
        return "$year.${if (month < 10) "0$month" else month}.${if (day < 10) "0$day" else day}"
    }

}