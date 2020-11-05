package net.bruhat.justdid

import java.util.TimeZone
import java.io.File
import java.nio.charset.StandardCharsets
import kotlin.collections.ArrayList

class EntryList {
    var entries: ArrayList<Entry> = ArrayList<Entry>();

    private lateinit var clock: Clock
    private lateinit var persistFile: File

    constructor(pPersistFile: File, pclock: Clock = Clock.System()) {
        clock = pclock
        persistFile = pPersistFile
        persistFile.forEachLine( StandardCharsets.UTF_8) {
            entries.add( Entry(it) )
        }
    }

    fun addEntry(chore: String): Entry {
        val epoch = clock.currentTimeMillis() / 1000
        val entry = net.bruhat.justdid.Entry(epoch, chore)
        entries.add(0, entry);
        return entry
    }

    override fun toString(): String {
        var dump: String = ""
        entries.listIterator().forEach {
            var line = it.toDisplayString(clock.timeZone())
            dump = dump.plus(line + "\n")
        }
        return dump
    }

    fun save() {
        var dump: String = ""
        entries.listIterator().forEach {
            dump = dump.plus(it.toString() + "\n")
        }
        persistFile.writeText( dump, StandardCharsets.UTF_8)
    }

    fun forTopN(n : Int = 5, callback: (chore: Entry) -> Unit ) {
        var i = 0;
        while (i < n && i < entries.size) {
            var entry = entries.get(i++);
            callback(entry)
        }
    }
}
