package net.bruhat.justdid

import android.content.Context
import java.util.TimeZone
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

class EntryListTest {
//    public class BogusTimeZone extends Timezone {
//
//    }

    // read an entrylist from an non-existing
    // create 3 entries
    // persist
    // read them back (in a new entrylist)
    // check the order / string

    @Before
    fun setup()
    {
    }

    @Test
    fun entries_added_at_front() {
        var testClock = Clock.Fake();
        testClock.millis = 1585702923;

        var persistedLog = File.createTempFile("EntryListTest_", ".tmp")

        var chorelist = EntryList(persistedLog, testClock)
        chorelist.addEntry( "chore 1")
        chorelist.addEntry( "chore 2")
        chorelist.addEntry( "chore 3")
        assertEquals( chorelist.entries[0].label, "chore 3")
        assertEquals( chorelist.entries[1].label, "chore 2")
        assertEquals( chorelist.entries[2].label, "chore 1")
    }

}
