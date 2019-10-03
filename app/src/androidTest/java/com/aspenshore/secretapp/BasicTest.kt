package com.aspenshore.secretapp

import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
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
        mainScreen.textEncrypted.matches("")
        mainScreen.editSource.stringValue = "S"
        mainScreen.textEncrypted.matches("h")
        mainScreen.editSource.typeText("o")
        mainScreen.textEncrypted.matches("Lh")
        mainScreen.editSource.typeText("mething to encrypt!")
        mainScreen.textEncrypted.matches("!GKBIXMV LG TMRSGVNLh")
        mainScreen.editSource.clearText()
        mainScreen.textEncrypted.matches("")
        mainScreen.editSource.stringValue = "!GKBIXMV LG TMRSGVNLh"
        mainScreen.textEncrypted.matches("Something to encrypt!")
        mainScreen.editSource.get().perform(replaceText("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        mainScreen.textEncrypted.matches("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")
    }

    @Test
    fun shouldBeAbleToCopyToClipboard() {
        mainScreen.editSource.stringValue = "Something to encrypt!"
        mainScreen.btnCopy.click()
        val clipboardManager  = activityRule.activity.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        assertEquals("!GKBIXMV LG TMRSGVNLh", clipboardManager.primaryClip.getItemAt(0).text)
    }
}
