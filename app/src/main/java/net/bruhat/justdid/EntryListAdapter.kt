package net.bruhat.justdid

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
