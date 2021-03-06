import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.juandavid.timegram.R
import com.example.juandavid.timegram.activities.DetailActivity
import com.example.juandavid.timegram.activities.MainActivity
import com.example.juandavid.timegram.fragments.MessageSelectedDialog
import com.example.juandavid.timegram.pojo.Event


/**
 * Provide views to RecyclerView with data from dataSet.
 *
 * Initialize the dataset of the Adapter.
 *
 * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
 */
class CustomAdapter(private val dataSet: MutableList<Event>) :
        RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    class ViewHolder(v: View, c: RecyclerViewFragment.OnListFragmentInteractionListener) : RecyclerView.ViewHolder(v) {
        val tvDate: TextView
        val tvObjective: TextView
        val tvDescr: TextView
        val tvCat: TextView
        val imgView: ImageView

        init {
            v.findViewById<CardView>(R.id.card).setOnClickListener {
                val intent = Intent(c as Context, DetailActivity::class.java)
                intent.putExtra("POSITION", adapterPosition)
                startActivity(v.context, intent,null)

            }

            v.findViewById<CardView>(R.id.card).setOnLongClickListener{
                val args = Bundle()
                args.putInt("ITEM_ID", RecyclerViewFragment.adapter.getItem(adapterPosition).id)
                args.putInt("ITEM_POSITION", adapterPosition)
                val dialog = MessageSelectedDialog()
                dialog.arguments = args
                dialog.show((c as MainActivity).supportFragmentManager, "ActionMessageItem")
                true
            }
            tvDate = v.findViewById(R.id.mtv)
            tvObjective = v.findViewById(R.id.title)
            tvDescr = v.findViewById(R.id.descr)
            tvCat = v.findViewById(R.id.cat)
            imgView = v.findViewById(R.id.img)

        }
    }

    fun insertItem(item: Event) {
        dataSet.add(itemCount, item)
        notifyItemInserted(itemCount - 1)
    }

    fun getItem(position: Int):Event{
        return dataSet[position]
    }

    fun deleteItem(position: Int) {
        dataSet.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view.
        val v = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.text_row_item, viewGroup, false)

        return ViewHolder(v, viewGroup.context as RecyclerViewFragment.OnListFragmentInteractionListener)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
         // Get element from your dataset at this position and replace the contents of the view
        // with that element
        viewHolder.tvDate.text = dataSet[position].date
        viewHolder.tvObjective.text = dataSet[position].objective
        viewHolder.tvDescr.text = dataSet[position].description
        viewHolder.tvCat.text = dataSet[position].category
        viewHolder.imgView.setImageResource(when(dataSet[position].category){
            "Personal" -> R.drawable.ic_person_black_24dp
            "Family" -> R.drawable.ic_wc_black_24dp
            "Work" -> R.drawable.ic_business_center_black_24dp
            "Work2" -> R.drawable.ic_drive_eta_black_24dp
            "Friends" -> R.drawable.ic_people_black_24dp
            "Health" -> R.drawable.ic_healing_black_24dp
            "Dentist" -> R.drawable.ic_insert_emoticon_black_24dp
            else -> R.drawable.ic_person_black_24dp
        })
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}