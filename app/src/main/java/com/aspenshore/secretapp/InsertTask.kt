package com.aspenshore.secretapp

import android.os.AsyncTask

class InsertTask(private var activity: MainActivity?) : AsyncTask<String, String, String>() {
    override fun doInBackground(vararg p0: String?): String {
        activity!!.db!!.messageDao().insertAll(Message(null, p0[0]))
        return ""
    }
}