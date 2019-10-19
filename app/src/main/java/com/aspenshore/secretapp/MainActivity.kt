package com.aspenshore.secretapp

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room.databaseBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    var database: AppDatabase? = null

    val db: AppDatabase? get() = database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        textEncrypted.text = encryptJNI(editSource.text.toString())

        editSource.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                textEncrypted.text = encryptJNI(s.toString())
            }
        })

        btnCopy.setOnClickListener {
            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboardManager.primaryClip = ClipData.newPlainText("simple text", textEncrypted.text)
            InsertTask(this).execute(editSource.text.toString());
            Toast.makeText(this, getString(R.string.text_save_to_clipboard), Toast.LENGTH_LONG)
                .show()
        }

        database = databaseBuilder(this, AppDatabase::class.java, "message").build()
    }

    external fun encryptJNI(path: String): String

    companion object {
        init {
            System.loadLibrary("encrypt-lib")
        }
    }
}
