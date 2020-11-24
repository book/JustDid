package net.bruhat.justdid

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class EntryListTest {

    fun write_four_entries(persistedLog: File, testClock: Clock.Fake) {
        var chorelist = EntryList(persistedLog, testClock)
        chorelist.addEntry("chore 1")
        testClock.millis++;
        chorelist.addEntry("chore 2")
        chorelist.addEntry("chore 3")
        testClock.millis++;
        chorelist.addEntry("chore 4")
        assertEquals(chorelist.entries[0].label, "chore 4")
        assertEquals(chorelist.entries[1].label, "chore 3")
        assertEquals(chorelist.entries[2].label, "chore 2")
        assertEquals(chorelist.entries[3].label, "chore 1")
        chorelist.save()
    }

    @Test
    fun survives_absent_file() {
        var persistedLog = File("does-not-exist.txt")
        var chorelist = EntryList(persistedLog)
        assertNotNull(chorelist)
    }

    @Test
    fun entries_added_at_front() {
        var testClock = Clock.Fake();
        testClock.millis = 1585702923;

        var persistedLog = File.createTempFile("EntryListTest_", ".tmp")
        write_four_entries(persistedLog, testClock)

        var chorelist = EntryList(persistedLog, testClock)
        assertEquals(chorelist.entries[0].label, "chore 4")
        assertEquals(chorelist.entries[1].label, "chore 3")
        assertEquals(chorelist.entries[2].label, "chore 2")
        assertEquals(chorelist.entries[3].label, "chore 1")
    }

    @Test
    fun for_top_n() {
        var testClock = Clock.Fake();
        testClock.timeZone = TimeZone.getTimeZone("GMT+0100");
        testClock.millis = 1585702923;

        var persistedLog = File.createTempFile("EntryListTest_", ".tmp")

        var chorelist = EntryList(persistedLog, testClock)
        chorelist.addEntry("laundry")
        testClock.millis++;
        chorelist.addEntry("litter")
        chorelist.addEntry("laundry")
        testClock.millis++;
        chorelist.addEntry("laundry")
        chorelist.addEntry("litter")
        testClock.millis++;

        var labels = ArrayList<String>()
        chorelist.forTopN(3, { labels.add(it.label) });
        assertEquals(labels.toString(), 2, labels.size)
        assertEquals("laundry", labels[0])
        assertEquals("litter", labels[1])

        labels.clear()
        chorelist.addEntry("litter")
        testClock.millis++;
        chorelist.addEntry("dishes")
        chorelist.addEntry("litter")

        chorelist.forTopN(3, { labels.add(it.label) });
        assertEquals(labels.toString(), 3, labels.size)
        assertEquals("litter", labels[0])
        assertEquals("laundry", labels[1])
        assertEquals("dishes", labels[2])

    }
}
