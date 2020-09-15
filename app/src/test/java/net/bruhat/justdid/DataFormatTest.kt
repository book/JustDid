package net.bruhat.justdid

import org.junit.Test

import org.junit.Assert.*

class DataFormatTest {

    @Test
    fun deserialization_of_valid_line() {
        val line = "1 12345 +0200 label text";
        var entry = net.bruhat.justdid.Entry(line)

        assertEquals(12345, entry.epoch)
        assertEquals("+0200", entry.offset)
        assertEquals("label text", entry.label)
    }

}
