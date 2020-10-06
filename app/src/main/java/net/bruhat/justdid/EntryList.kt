package net.bruhat.justdid

import android.icu.util.TimeZone
import android.icu.util.TimeZone.SHORT_GMT
import java.util.*
import kotlin.collections.ArrayList

class EntryList {
    var entries: ArrayList<Entry> = ArrayList<Entry>();

    fun addEntry(chore: String) : Entry {
        val epoch = System.currentTimeMillis()
        val tz = TimeZone.getDefault()
        val offset = tz.getDisplayName(tz.inDaylightTime(Date(epoch)), SHORT_GMT )
        val entry = net.bruhat.justdid.Entry( epoch, offset, chore )
        entries.add(entry);
        return entry
    }

    override fun toString() : String {
        var dump : String = ""
        entries.listIterator().forEach {
            dump = dump.plus( it.toString() + "\n" )
        }
        return dump
    }
}
