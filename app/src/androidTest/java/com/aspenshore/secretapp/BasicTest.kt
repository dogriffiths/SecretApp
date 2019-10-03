package com.aspenshore.secretapp

import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.aspenshore.secretapp.screen.MainScreen
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BasicTest {
    @get:Rule
    var activityRule: ActivityTestRule<MainActivity>
            = ActivityTestRule(MainActivity::class.java)

    val mainScreen: MainScreen = MainScreen()

    @Test
    fun willHaveCorrectInitialText() {
        mainScreen.textEncrypted.matches("")
    }

    @Test
    fun willCorrectlyEncrypt() {
        with(mainScreen) {
            textEncrypted.matches("")
            editSource.stringValue = "S"
            textEncrypted.matches("h")
            editSource.typeText("o")
            textEncrypted.matches("Lh")
            editSource.typeText("mething to encrypt!")
            textEncrypted.matches("!GKBIXMV LG TMRSGVNLh")
            editSource.clearText()
            textEncrypted.matches("")
            editSource.stringValue = "!GKBIXMV LG TMRSGVNLh"
            textEncrypted.matches("Something to encrypt!")
            editSource.replaceText("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")
            textEncrypted.matches("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")
        }
    }

    @Test
    fun shouldBeAbleToCopyToClipboard() {
        mainScreen.editSource.stringValue = "Something to encrypt!"
        Espresso.closeSoftKeyboard()
        mainScreen.btnCopy.click()
        val clipboardManager  = activityRule.activity.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        assertEquals("!GKBIXMV LG TMRSGVNLh", clipboardManager.primaryClip.getItemAt(0).text)
    }
}
