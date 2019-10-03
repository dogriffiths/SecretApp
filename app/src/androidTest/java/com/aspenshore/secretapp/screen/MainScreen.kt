package com.aspenshore.secretapp.screen

import androidx.test.espresso.matcher.ViewMatchers.withId
import com.aspenshore.secretapp.MainActivity
import com.aspenshore.secretapp.R
import uk.co.blackpepper.relish.espresso.EspressoWidget
import uk.co.blackpepper.relish.espresso.InputText
import uk.co.blackpepper.relish.espresso.Screen
import uk.co.blackpepper.relish.espresso.Text

/**
 * Instantiates a new Screen.
 */
class MainScreen : Screen(MainActivity::class.java) {

    val textEncrypted: Text get() = Text(withId(R.id.textEncrypted), this)

    val editSource: InputText get() = InputText(withId(R.id.editSource), this)

    val btnCopy: EspressoWidget get() = EspressoWidget(withId(R.id.btnCopy), this)
}
