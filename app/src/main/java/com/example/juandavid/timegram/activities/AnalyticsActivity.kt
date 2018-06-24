package com.example.juandavid.timegram.activities

import android.app.ProgressDialog
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import com.example.juandavid.timegram.R
import com.example.juandavid.timegram.database.AppDatabase
import com.example.juandavid.timegram.pojo.Category
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
        analytics_spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                getMetrics(position)
            }

        }
    }

    fun getMetrics(id : Int){
        var sumMin = 0.0
        var count = 0
        val map: HashMap<Int, Int> = HashMap()
        for (i in lista) {
            if(id==0 || Category.fromId(id - 1).text == i.category) {
                val dateFormat = SimpleDateFormat("HH:mm")
                try {
                    val date = dateFormat.parse(i.objective).time
                    val date2 = dateFormat.parse(i.realtime).time
                    val mins = (date - date2) / (1000 * 60)
                    sumMin += mins
                    count++
                    map[mins.toInt()] = (map[mins.toInt()] ?: 0) + 1
                } catch (e: ParseException) {
                }
            }
        }

        val average = sumMin / count
        val mText = average.toString()
        time_text.text = if (mText.length > 4) mText.substring(0, 4) else mText

        val arr = ArrayList<DataPoint>()
        var minX = 0.0
        var maxX = 0.0

            for (i in map.toSortedMap()) {
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
            getMetrics(0)
            return 0
        }

        override fun onPostExecute(result: Int?) {
            super.onPostExecute(result)
            if (dialog.isShowing)
                dialog.dismiss()
        }
    }
}
