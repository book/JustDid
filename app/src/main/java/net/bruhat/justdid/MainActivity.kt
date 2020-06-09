package net.bruhat.justdid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun millisToString(millis: Long): String {
        val pattern = "yyyy-MM-dd HH:mm:ss Z"
        val simpleDateFormat = SimpleDateFormat(pattern)
        val dateTimeStr = simpleDateFormat.format(Date(millis))
        return dateTimeStr
    }

    fun addToTaskLog(view: View) {
        val taskLog = findViewById<TextView>(R.id.taskLog)
        val task    = findViewById<EditText>(R.id.editText)
        val dateTimeStr = millisToString(System.currentTimeMillis())
        val newText = dateTimeStr + " " + task.text + "\n" + taskLog.text
        taskLog.setText( newText.toCharArray(), 0, newText.length )
        task.setText("")
    }
}