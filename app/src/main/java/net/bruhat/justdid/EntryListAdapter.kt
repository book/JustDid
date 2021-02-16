package net.bruhat.justdid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EntryListAdapter (val entryList: EntryList) :
        RecyclerView.Adapter<EntryListAdapter.EntryListViewHolder>() {

        // Describes an item view and its place within the RecyclerView
        class EntryListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val entryListTextView: TextView = itemView.findViewById(R.id.entry_text)

            fun bind(entrylist: EntryList, position: Int) {
                entryListTextView.text = entrylist.entryDisplayString(position)
            }
        }

        // Returns a new ViewHolder
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryListViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.entry_item, parent, false)

            return EntryListViewHolder(view)
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
