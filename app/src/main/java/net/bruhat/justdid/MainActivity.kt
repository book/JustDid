package net.bruhat.justdid

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment.getExternalStorageDirectory
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.io.File
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var entryList: EntryList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        entryList = EntryList()
        val taskLog = findViewById<TextView>(R.id.taskLog)
        val newText = readTaskLog()
        taskLog.setText(newText.toCharArray(), 0, newText.length)
    }

    // Storage Permissions
    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf<String>(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    private fun verifyStoragePermissions(activity: Activity?) {
        // Check if we have write permission
        val permission =
            ActivityCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                activity,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
            )
        }
    }

    private fun getTaskLogFile(): File {
        verifyStoragePermissions(this);
        val name = getString(R.string.just_did_filename)
        // path = /data/user/0/net.bruhat.justdid/files/
        val path = getFilesDir()
        return File(path, name)
    }

    private fun writeString(s: String) {
        getTaskLogFile().writeText( entryList.toString(), StandardCharsets.UTF_8)
    }

    private fun readTaskLog(): String {
        val logFile = getTaskLogFile()
        if (!logFile.exists()) {
            return ""
        }
        return logFile.readText(StandardCharsets.UTF_8)
    }

    private fun millisToString(millis: Long): String {
        val pattern = "yyyy-MM-dd HH:mm:ss Z"
        val simpleDateFormat = SimpleDateFormat(pattern)
        val dateTimeStr = simpleDateFormat.format(Date(millis))
        return dateTimeStr
    }

    fun addToTaskLog(view: View) {
        val taskLog = findViewById<TextView>(R.id.taskLog)
        val task = findViewById<EditText>(R.id.editText)
        val entry = entryList.addEntry( task.text.toString() )

        writeString(entry.toString() + "\n")
        val newText = entry.toString() + "\n" + taskLog.text.toString()

        taskLog.setText(newText.toCharArray(), 0, newText.length)
        task.setText("")
    }
}
