package net.bruhat.justdid

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.bruhat.justdid.R.id.entry_text
import java.text.SimpleDateFormat

class EntryListAdapter(val entryList: EntryList) :
    RecyclerView.Adapter<EntryListAdapter.EntryListViewHolder>() {

    // Describes an item view and its place within the RecyclerView
    class EntryListViewHolder(entryListAdapter: EntryListAdapter, itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val entryListAdapter = entryListAdapter
        private val entryListTextView: TextView = itemView.findViewById(entry_text)
        private val delButton: Button = itemView.findViewById(R.id.del_button)
        private val editButton: Button = itemView.findViewById(R.id.edit_button)
        private val addButton: Button = itemView.findViewById(R.id.add_button)

        fun bind(entrylist: EntryList, position: Int) {
            entryListTextView.text = entrylist.entryDisplayString(position)
            addButton.setOnClickListener(
                View.OnClickListener {
                    // add an item (same text) to the entry list
                    entrylist.addEntry(entrylist.entries[position].label)
                    entryListAdapter.notifyDataSetChanged()
                }
            )
            editButton.setOnClickListener(
                View.OnClickListener {

                    var dialog = Dialog(itemView.context)
                    dialog.setContentView(R.layout.edit_entry)
                    dialog.findViewById<EditText>(R.id.entry_text).setText( entrylist.entries[position].label )
                    // TODO: make actual date and time strings to view

                    // get date and time  as strings
                    var clock = entrylist.getClock()
                    var epochMillis = 1000 * entrylist.entries[position].epoch;

                    val ymdPattern = "yyyy-MM-dd"
                    val ymdDateFormat = SimpleDateFormat(ymdPattern)
                    ymdDateFormat.timeZone = clock.timeZone()
                    val dateStr = ymdDateFormat.format(epochMillis)
                    dialog.findViewById<EditText>(R.id.entry_date).setText( dateStr )

                    val hmsPattern = "HH:mm:ss"
                    val hmsDateFormat = SimpleDateFormat(hmsPattern)
                    hmsDateFormat.timeZone = clock.timeZone()
                    val timeStr = hmsDateFormat.format(epochMillis)
                    dialog.findViewById<EditText>(R.id.entry_time).setText( timeStr )

                    // when changed, compute and update the new epoch from date + time
                    // TODO: need to hook up "save" and "cancel" buttons
                    dialog.show()
                }
            )
            delButton.setOnClickListener(
                View.OnClickListener {
                    // delete the item ast position to the entry list
                    entrylist.entries.removeAt(position)
                    // notify the recycler that data has changed
                    entryListAdapter.notifyDataSetChanged()
                }
            )
        }
    }

    // Returns a new ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.entry_item, parent, false)

        return EntryListViewHolder(this, view)
    }

    // Returns size of data list
    override fun getItemCount(): Int {
        return entryList.size()
    }

    // Displays data at a certain position
    override fun onBindViewHolder(holder: EntryListViewHolder, position: Int) {
        holder.bind(entryList, position)
    }
}
