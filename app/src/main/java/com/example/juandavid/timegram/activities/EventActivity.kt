package com.example.juandavid.timegram.activities


import RecyclerViewFragment
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.example.juandavid.timegram.R
import com.example.juandavid.timegram.database.AppDatabase
import com.example.juandavid.timegram.pojo.Event
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.activity_event.*
import java.text.SimpleDateFormat
import java.util.*

class EventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        var dateString = SimpleDateFormat("dd/MM/yyyy").format(Date(calendar.date))

        calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            dateString = dayOfMonth.toString() + "/" + (month + 1) + "/" + year
        }

        button_cancel.setOnClickListener{_ -> finish() }
        button_save.setOnClickListener{view ->

            val minute = (if (tp_timepicker.minute < 10) "0" else "") + tp_timepicker.minute
            val e = Event(date = dateString,
            category = spin_cat.selectedItem.toString(),
                    objective = tp_timepicker.hour.toString() + ":"+ minute,
                    description = editText.text.toString(),
                    realtime = null)
            AsyncInsert().execute(e)
            RecyclerViewFragment.adapter.insertItem(e)
            FancyToast.makeText(view.context,
                    "Item added successfully",
                    Snackbar.LENGTH_LONG,
                    FancyToast.SUCCESS,
                    false).show()
            finish()
        }
    }

    private inner class AsyncInsert : AsyncTask<Event, Void, Int>() {
        override fun doInBackground(vararg params: Event): Int? {
            AppDatabase.getInstance(baseContext).eventDao().insert(params[0])
            return 0
        }
    }
}
