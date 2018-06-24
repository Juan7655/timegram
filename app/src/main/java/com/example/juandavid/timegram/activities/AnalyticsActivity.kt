package com.example.juandavid.timegram.activities

import android.app.ProgressDialog
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.juandavid.timegram.R
import com.example.juandavid.timegram.database.AppDatabase
import com.example.juandavid.timegram.pojo.Event
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint
import kotlinx.android.synthetic.main.activity_analytics.*
import java.text.ParseException
import java.text.SimpleDateFormat
import kotlin.math.max
import kotlin.math.min

class AnalyticsActivity : AppCompatActivity() {

    lateinit var lista:List<Event>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analytics)
        AsyncRetrieve(this).execute()
    }

    fun getMetrics(){
        var sumMin = 0.0
        val map: HashMap<Int, Int> = HashMap()
        for (i in lista) {
            val dateFormat = SimpleDateFormat("HH:mm")
            try {
                val date = dateFormat.parse(i.objective).time
                val date2 = dateFormat.parse(i.realtime).time
                val diff = date - date2
                val mins = diff / (1000 * 60)
                sumMin += mins
                map[mins.toInt()] = (map[mins.toInt()] ?:0) + 1
            } catch (e: ParseException) {
            }
        }

        val average = sumMin / lista.size
        time_text.text = average.toString().substring(0, 4)

        val arr = ArrayList<DataPoint>()
        var minX = 0.0
        var maxX = 0.0

            for (i in map) {
                arr.add(DataPoint(i.key.toDouble(), i.value.toDouble()))
                minX = min(minX, i.key.toDouble())
                maxX = max(maxX, i.key.toDouble())
            }

        val series = BarGraphSeries<DataPoint>(arr.toTypedArray())
        graph.removeAllSeries()
        graph.addSeries(series)

        graph.viewport.isXAxisBoundsManual = true
        graph.viewport.isYAxisBoundsManual = true
        graph.viewport.setMinX(minX)
        graph.viewport.setMaxX(maxX)
        graph.viewport.setMinY(0.0)

    }

    private inner class AsyncRetrieve(mContext:AnalyticsActivity) : AsyncTask<Void, Void, Int>() {
        val dialog = ProgressDialog(mContext)


        override fun onPreExecute() {
            dialog.setMessage("Doing something, please wait.")
            dialog.show()
            super.onPreExecute()
        }

        override fun doInBackground(vararg params: Void): Int? {
            lista = AppDatabase.getInstance(baseContext).eventDao().doneAppointments
            getMetrics()
            return 0
        }

        override fun onPostExecute(result: Int?) {
            super.onPostExecute(result)
            if (dialog.isShowing)
                dialog.dismiss()
        }
    }
}
