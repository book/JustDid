package net.bruhat.justdid

import java.io.File
import net.bruhat.justdid.Clock
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

    fun save() {
        persistFile.writeText( toString(), StandardCharsets.UTF_8)
    }
}
