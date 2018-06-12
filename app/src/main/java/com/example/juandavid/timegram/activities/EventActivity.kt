package com.example.juandavid.timegram.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.juandavid.timegram.R
import com.example.juandavid.timegram.pojo.Category
import com.example.juandavid.timegram.pojo.Event
import kotlinx.android.synthetic.main.activity_event.*
import com.example.juandavid.timegram.database.AppDatabase
import android.os.AsyncTask




class EventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        button_cancel.setOnClickListener{_ -> finish() }
        button_save.setOnClickListener{_ ->
            val e = Event(date = calendar.date.toString(),
            category = Category.fromId(spin_cat.selectedItemId.toInt()),
                    objective = tp_timepicker.hour.toString() + ":"+ tp_timepicker.minute,
                    description = editText.text.toString(),
                    realtime = null)
            AsyncInsert().execute(e)
            Log.d("EVENT TEST", e.toString())
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
