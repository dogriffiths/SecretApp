package com.aspenshore.secretapp

import android.content.ClipboardManager
import android.content.Context
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BasicTest {
    @get:Rule
    var activityRule: ActivityTestRule<MainActivity>
            = ActivityTestRule(MainActivity::class.java)

    @Test
    fun willHaveCorrectInitialText() {
        // Check that the text was changed.
        onView(withId(R.id.textEncrypted))
            .check(matches(withText("")))

    }

    @Test
    fun willCorrectlyEncrypt() {
        onView(withId(R.id.textEncrypted))
            .check(matches(withText("")))

        onView(withId(R.id.editSource))
            .perform(typeText("S"), closeSoftKeyboard())

        onView(withId(R.id.textEncrypted))
            .check(matches(withText("h")))

        onView(withId(R.id.editSource))
            .perform(typeText("o"), closeSoftKeyboard())

        onView(withId(R.id.textEncrypted))
            .check(matches(withText("Lh")))

        onView(withId(R.id.editSource))
            .perform(typeText("mething to encrypt!"), closeSoftKeyboard())

        onView(withId(R.id.textEncrypted))
            .check(matches(withText("!GKBIXMV LG TMRSGVNLh")))

        onView(withId(R.id.editSource))
            .perform(clearText());

        onView(withId(R.id.textEncrypted))
            .check(matches(withText("")))

        onView(withId(R.id.editSource))
            .perform(typeText("!GKBIXMV LG TMRSGVNLh"), closeSoftKeyboard())

        onView(withId(R.id.textEncrypted))
            .check(matches(withText("Something to encrypt!")))
    }

    @Test
    fun shouldBeAbleToCopyToClipboard() {
        onView(withId(R.id.editSource))
            .perform(typeText("Something to encrypt!"), closeSoftKeyboard())

        onView(withId(R.id.btnCopy)).perform(click());

        val activity = activityRule.activity
        val clipboardManager  = activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        assertEquals("!GKBIXMV LG TMRSGVNLh", clipboardManager.primaryClip.getItemAt(0).text)
    }
}
