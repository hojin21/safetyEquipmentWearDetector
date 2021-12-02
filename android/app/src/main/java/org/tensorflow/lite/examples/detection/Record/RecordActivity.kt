package org.tensorflow.lite.examples.detection.Record

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.tensorflow.lite.examples.detection.R
import org.tensorflow.lite.examples.detection.database.Record
import org.tensorflow.lite.examples.detection.database.RecordDatabase
import java.text.SimpleDateFormat
import java.util.*

class RecordActivity : AppCompatActivity() {
    private lateinit var tv_no_result: TextView
    private lateinit var tv_start_date: TextView
    private lateinit var tv_end_date: TextView
    private lateinit var iv_back: ImageView
    private lateinit var iv_search: ImageView
    private lateinit var recycler_view: RecyclerView
    private lateinit var db: RecordDatabase
    val searchSdf = SimpleDateFormat("yyyy.MM.dd")
    private val resultList = mutableListOf<Record>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.record_activity)
        tv_no_result = findViewById(R.id.tv_no_result)
        tv_start_date = findViewById(R.id.tv_start_date)
        tv_end_date = findViewById(R.id.tv_end_date)
        iv_back = findViewById(R.id.iv_back)
        iv_search = findViewById(R.id.iv_search)
        recycler_view = findViewById(R.id.recycler_view)
        recycler_view.layoutManager = LinearLayoutManager(this)

        val adapter = RecordAdapter(resultList as ArrayList<Record>?)
        recycler_view.adapter = adapter

        tv_no_result.visibility = View.VISIBLE
        setInitDay()
        addListener()
    }

    @SuppressLint("SimpleDateFormat")
    private fun setInitDay() {
        val currTime = SimpleDateFormat("yyyy.MM.dd").format(System.currentTimeMillis())
        tv_start_date.text = currTime
        tv_end_date.text = currTime
    }

    @SuppressLint("SetTextI18n")
    private fun addListener() {
        iv_back.setOnClickListener { finish() }
        iv_search.setOnClickListener { getSearchDB() }
        tv_start_date.setOnClickListener {
            val listener =
                DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, month: Int, day: Int ->
                    tv_start_date.text = gerStringTimeFormat(year, month + 1, day)
                }
            val (year, month, day) = tv_start_date.text.split(".").map { it.toInt() }
            val dialog = DatePickerDialog(this, listener, year, month - 1, day)
            dialog.show()
        }
        tv_end_date.setOnClickListener {
            val listener =
                DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, month: Int, day: Int ->
                    tv_end_date.text = gerStringTimeFormat(year, month + 1, day)
                }
            val (year, month, day) = tv_end_date.text.split(".").map { it.toInt() }
            val dialog = DatePickerDialog(this, listener, year, month - 1, day)
            dialog.show()
        }
    }

    private fun getSearchDB() {
        tv_no_result.visibility = View.GONE
        db = RecordDatabase.getInstance(this)!!
        AsyncTask.execute {
            resultList.clear()
            val totalList = db.recordDao().getAll()
            if(totalList.isEmpty()) tv_no_result.visibility = View.VISIBLE

            val startTime = searchSdf.parse(tv_start_date.text.toString())
            val endTime = searchSdf.parse(tv_end_date.text.toString())

            for (item in totalList) {
                val (itemYear, itemMonth, itemDay) = item.saveTime.split("_").map { it.toInt() }
                val searchTime = searchSdf.parse(gerStringTimeFormat(itemYear, itemMonth, itemDay))!!
                if((searchTime == startTime || searchTime.after(startTime)) && (searchTime == endTime || searchTime.before(endTime))){
                    resultList.add(item)
                }
            }
        }
        recycler_view.adapter?.notifyDataSetChanged()
    }

    private fun gerStringTimeFormat(year: Int, month: Int, day: Int): String {
        return "$year.${if (month < 10) "0$month" else month}.${if (day < 10) "0$day" else day}"
    }
}