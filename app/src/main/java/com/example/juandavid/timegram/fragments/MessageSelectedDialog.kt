package com.example.juandavid.timegram.fragments

import RecyclerViewFragment
import android.app.AlertDialog
import android.app.Dialog
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.DialogFragment
import com.example.juandavid.timegram.R
import com.example.juandavid.timegram.database.AppDatabase
import com.example.juandavid.timegram.pojo.Event

/**
 * Created by juandavid on 13/06/18.
 */

class MessageSelectedDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the Builder class for convenient dialog construction
        val builder = AlertDialog.Builder(activity)
        builder.setMessage(R.string.msg_dialog_title)
                .setNeutralButton(R.string.msg_dialog_cancel) { _, _ ->
                    //No action required
                }
                .setNegativeButton(R.string.msg_dialog_delete) { _, _ ->
                    val posItem = arguments!!.getInt("ITEM_POSITION")
                    AsyncDelete().execute(RecyclerViewFragment.adapter.getItem(posItem))
                    RecyclerViewFragment.adapter.deleteItem(posItem)
                }
        // Create the AlertDialog object and return it
        return builder.create()
    }

    private inner class AsyncDelete : AsyncTask<Event, Void, Void>() {
        override fun doInBackground(vararg params: Event): Void? {
            AppDatabase.getInstance(activity).eventDao()
                    .deleteEvent(
                            params[0].date,
                            params[0].objective,
                            params[0].description,
                            params[0].category)
            return null
        }
    }
}
