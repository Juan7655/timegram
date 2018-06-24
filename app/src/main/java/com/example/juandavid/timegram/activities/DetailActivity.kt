package com.example.juandavid.timegram.activities

import RecyclerViewFragment
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.example.juandavid.timegram.R
import com.example.juandavid.timegram.database.AppDatabase
import com.example.juandavid.timegram.pojo.Event
import com.google.firebase.database.FirebaseDatabase
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*
import java.lang.Integer.parseInt

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)

        val pos = intent.getIntExtra("POSITION", 0)
        val e = RecyclerViewFragment.adapter.getItem(pos)
        val tempTime = e.objective.split(":")
        detail_time.hour = parseInt(tempTime[0])
        detail_time.minute = parseInt(tempTime[1])

        detail_date.text = e.date
        detail_title.text = e.objective
        detail_descr.text = e.description
        detail_img.setImageResource(when(e.category){
            "Personal" -> R.drawable.ic_person_black_24dp
            "Family" -> R.drawable.ic_wc_black_24dp
            "Work" -> R.drawable.ic_business_center_black_24dp
            "Work2" -> R.drawable.ic_drive_eta_black_24dp
            "Friends" -> R.drawable.ic_people_black_24dp
            "Health" -> R.drawable.ic_healing_black_24dp
            "Dentist" -> R.drawable.ic_insert_emoticon_black_24dp
            else -> R.drawable.ic_person_black_24dp
        })

        detail_fab.setOnClickListener { view ->
            val minute = (if (detail_time.minute < 10) "0" else "") + detail_time.minute.toString()
            e.realtime = detail_time.hour.toString() + ":"+ minute

            AsyncUpdate().execute(e)
            FancyToast.makeText(view.context,
                    "Item updated",
                    Snackbar.LENGTH_LONG,
                    FancyToast.INFO,
                    false).show()
            RecyclerViewFragment.adapter.deleteItem(pos)
            finish()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private inner class AsyncUpdate : AsyncTask<Event, Void, Int>() {
        override fun doInBackground(vararg params: Event): Int? {
            AppDatabase.getInstance(baseContext).eventDao()
                    .update(params[0].date,
                            params[0].objective,
                            params[0].description,
                            params[0].category,
                            params[0].realtime)

            val database = FirebaseDatabase.getInstance().getReference("TIMEGRAM_EVENTS")
            val list = AppDatabase.getInstance(baseContext).eventDao().doneAppointments
            for (i in list)
                database.child(i.id.toString()).setValue(i)
            return 0
        }
    }

}
