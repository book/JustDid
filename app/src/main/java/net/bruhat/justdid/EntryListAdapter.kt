package net.bruhat.justdid

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EntryListAdapter(val entryList: EntryList) :
    RecyclerView.Adapter<EntryListAdapter.EntryListViewHolder>() {

    // Describes an item view and its place within the RecyclerView
    class EntryListViewHolder(entryListAdapter: EntryListAdapter, itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val entryListAdapter = entryListAdapter
        private val entryListTextView: TextView = itemView.findViewById(R.id.entry_text)
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
                    // create the dialog
                     val builder: AlertDialog.Builder? = AlertDialog.Builder(itemView.context)

            // 2. Chain together various setter methods to set the dialog characteristics
                    builder?.setMessage("zlonk")
                           ?.setTitle("kapow")
                           ?.setPositiveButton("YES",
                            DialogInterface.OnClickListener { dialog, id ->
                                // Send the positive button event back to the host activity
                                // listener.onDialogPositiveClick(this)
                            })


                   // 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
                    val dialog: AlertDialog? = builder?.create()

                    dialog?.show()
                    // signal data might have changed
                    entryListAdapter.notifyDataSetChanged()
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
