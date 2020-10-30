package net.bruhat.justdid

import java.text.SimpleDateFormat
import java.util.TimeZone

class Entry {

    // TODO: how to use class-level SerialVersionUID ?
    private val ourSerialVersionUID: Long = 1

    var epoch: Long = 0
    lateinit var label: String

    constructor(pepoch: Long, plabel: String) {
        epoch = pepoch
        label = plabel;
    }

    constructor(line: String) {
        val versionedLine: List<String> = line.split(" ", ignoreCase = false, limit = 2)
        if (versionedLine[0].equals("1")) {
            val parts: List<String> = versionedLine[1].split(" ", ignoreCase = false, limit = 2)
            epoch = parts[0].toLong();
            label = parts[1]
        } else {
            throw RuntimeException(line)
        }
    }

    fun toDisplayString(tz: TimeZone): String {
        val pattern = "yyyy-MM-dd HH:mm:ss Z"
        val simpleDateFormat = SimpleDateFormat(pattern)
        simpleDateFormat.timeZone = tz
        val dateTimeStr = simpleDateFormat.format(epoch * 1000)
        return String.format("%s %s", dateTimeStr, label )
    }
    override fun toString(): String {
        return String.format( "%d %d %s", ourSerialVersionUID, epoch, label )
    }
}
