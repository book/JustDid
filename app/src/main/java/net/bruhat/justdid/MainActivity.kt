package net.bruhat.justdid

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import java.io.File


class MainActivity : AppCompatActivity() {
    private lateinit var entryList: EntryList
    private lateinit var buttonList: ArrayList<Button>
    private lateinit var buttonMap: HashMap<Int,String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        entryList = EntryList(getTaskLogFile())
        buttonList = ArrayList<Button>()
        buttonMap = HashMap<Int,String>()

        buttonList.add( findViewById<Button>(R.id.button1) )
        buttonList.add( findViewById<Button>(R.id.button2) )
        buttonList.add( findViewById<Button>(R.id.button3) )

        var idx = 0
        entryList.forTopN( 3, {
            val button_id = buttonList[idx].getId()
            buttonList[idx].text = it.label
            buttonMap.set(button_id, it.label)
            ++idx
        })
        while( idx < buttonList.size) {
            buttonList[idx].isActivated = false
            buttonList[idx].setText( "...")
        }

        val taskLog = findViewById<TextView>(R.id.taskLog)
        taskLog.setText(entryList.toString())

        val task = findViewById<EditText>(R.id.editText)
        task.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEND -> {
                    addToTaskLog(v)
                    true
                }
                else -> false
            }
        }
        task.setOnKeyListener { v, keyCode, event ->
            return@setOnKeyListener when (keyCode) {
                KeyEvent.KEYCODE_ENTER -> {
                    if (event.getAction() === KeyEvent.ACTION_DOWN) {
                        addToTaskLog(v)
                        true
                    } else {
                        false
                    }
                }
                else -> false
            }
        }
    }

    // Storage Permissions
    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf<String>(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private fun buildButtonFor( chore : String ) {
        var newButton: Button = Button(this)
        newButton.setId(View.generateViewId())
        newButton.setText(chore)

        // set constraints
        // see: ConstraintLayout, ConstraintSet
        val mainLayout = findViewById<ConstraintLayout>(R.id.mainLayout)
        mainLayout.addView( newButton)

        buttonList.add(newButton);
    }


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

    fun addToTaskLog(view: View) {
        val taskLog = findViewById<TextView>(R.id.taskLog)
        val task = findViewById<EditText>(R.id.editText)
        val entry = entryList.addEntry(task.text.toString())
        entryList.save()

        taskLog.text = entryList.toString()
        task.setText("")
        // reorg buttons
    }

    fun clickButton( view:View) {
        val id = view.getId()
        val text = buttonMap.get(id)
        if (text != null) {
            val taskLog = findViewById<TextView>(R.id.taskLog)
            val entry = entryList.addEntry(text)
            entryList.save()
            taskLog.text = entryList.toString()
            // reorg buttons
        }
    }
}
