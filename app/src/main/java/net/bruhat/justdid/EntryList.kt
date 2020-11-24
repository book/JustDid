package net.bruhat.justdid

import java.io.File
import java.nio.charset.StandardCharsets
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class EntryList {
    var entries: ArrayList<Entry> = ArrayList<Entry>()
    var entriesByLabel: HashMap<String, ArrayList<Entry>> =
        LinkedHashMap<String, ArrayList<Entry>>()

    private lateinit var clock: Clock
    private lateinit var persistFile: File

    constructor(pPersistFile: File, pclock: Clock = Clock.System()) {
        clock = pclock
        persistFile = pPersistFile
        if (persistFile.exists()) {
            persistFile.forEachLine(StandardCharsets.UTF_8) {
                var entry = Entry(it)
                entries.add(entry)
                listForLabel(entry.label).add(entry)
            }
        }
    }

    private fun listForLabel(label: String): ArrayList<Entry> {
        var list = entriesByLabel.get(label)
        if (list == null) {
            list = ArrayList<Entry>()
            entriesByLabel.put(label, list)
        }
        return list
    }

    fun addEntry(chore: String): Entry {
        val epoch = clock.currentTimeMillis() / 1000
        val entry = net.bruhat.justdid.Entry(epoch, chore)
        entries.add(0, entry)
        listForLabel(entry.label).add(0, entry)
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
        persistFile.writeText(dump, StandardCharsets.UTF_8)
    }

    // top N items in a tally of all chores
    fun forTopN(n: Int = 5, callback: (chore: Entry) -> Unit) {
        var topEntries =
            (entriesByLabel.entries.sortedBy { it.value.size }).asReversed()
        var i = 0
        while (i < n && i < topEntries.size) {
            callback(topEntries.get(i++).value[0])
        }
    }
}
