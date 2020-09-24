package net.bruhat.justdid

import android.text.TextUtils.split

class Entry {

    // TODO: how to use class-level SerialVersionUID ?
    private val ourSerialVersionUID: Long = 1

    var epoch: Long = 0
    lateinit var offset: String
    lateinit var label: String

    constructor(pepoch: Long, poffset: String, plabel: String) {
        epoch = pepoch
        offset = poffset
        label = plabel;
    }

    constructor(line: String) {
        val versionedLine: List<String> = line.split(" ", ignoreCase = false, limit = 2)
        if (versionedLine[0].equals("1")) {
            val parts: List<String> = versionedLine[1].split(" ", ignoreCase = false, limit = 3)
            epoch = parts[0].toLong();
            offset = parts[1]
            label = parts[2]
        } else {
            throw RuntimeException(line)
        }
    }

    override fun toString(): String {
        return String.format( "%d %d %s %s", ourSerialVersionUID, epoch, offset, label )
    }
}
