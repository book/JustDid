package net.bruhat.justdid

import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class EntryList {
    var entries: ArrayList<Entry> = ArrayList<Entry>();

    constructor( persistFile: File) {
        // readFile
    }

    fun addEntry(chore: String) : Entry {
        val epoch = System.currentTimeMillis()
        val entry = net.bruhat.justdid.Entry( epoch, chore )
        entries.add(0, entry);
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
