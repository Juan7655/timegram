package com.example.juandavid.timegram.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.juandavid.timegram.R
import kotlinx.android.synthetic.main.activity_main.*

import RecyclerViewFragment
import kotlinx.android.synthetic.main.recycler_view_frag.*

class MainActivity : AppCompatActivity() {

    val fragment = RecyclerViewFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val transaction = supportFragmentManager.beginTransaction()

        transaction.replace(R.id.sample_content_fragment, fragment)
        transaction.commit()

        fab.setOnClickListener { _ ->
            startActivity(Intent(this, EventActivity::class.java))
        }
    }

    override fun onResume() {
        fragment.initDataset()
        fragment.recyclerView.adapter.notifyDataSetChanged()
        super.onResume()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)

        }
    }
}
