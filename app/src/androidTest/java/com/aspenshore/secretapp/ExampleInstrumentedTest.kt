package com.aspenshore.secretapp


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
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
        onView(withId(R.id.editSource))
            .perform(typeText("Something to encrypt!"), closeSoftKeyboard())

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

}
