package net.bruhat.justdid

import org.junit.Test

import org.junit.Assert.*

class DataFormatTest {

    @Test
    fun deserialization_of_valid_line() {
        val line = "1 12345 label text";
        var entry = net.bruhat.justdid.Entry(line)

        assertEquals(12345, entry.epoch)
        assertEquals("label text", entry.label)
    }

    @Test
    fun serialization_of_entry() {
        var entry = net.bruhat.justdid.Entry( 1234567890, "laundry")
        var line = entry.toString()

        assertEquals( "1 1234567890 laundry", line )
    }

}