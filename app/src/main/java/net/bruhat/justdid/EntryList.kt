package net.bruhat.justdid

import java.io.File
import net.bruhat.justdid.Clock
import kotlin.collections.ArrayList

class EntryList {
    var entries: ArrayList<Entry> = ArrayList<Entry>();

    private lateinit var clock: Clock

    constructor(persistFile: File, pclock: Clock = Clock.System()) {
        clock = pclock
        // readFile
    }

    fun addEntry(chore: String): Entry {
        val epoch = clock.currentTimeMillis()
        val entry = net.bruhat.justdid.Entry(epoch, chore)
        entries.add(0, entry);
        return entry
    }

    override fun toString(): String {
        var dump: String = ""
        entries.listIterator().forEach {
            dump = dump.plus(it.toString() + "\n")
        }
        return dump
    }
}
