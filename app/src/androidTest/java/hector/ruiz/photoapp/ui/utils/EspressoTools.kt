package hector.ruiz.photoapp.ui.utils

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.Matchers.not

object EspressoTools {

    fun viewDisplayed(view: Int): ViewInteraction = getView(view).check(matches(isDisplayed()))

    fun viewNotDisplayed(view: Int): ViewInteraction =
        getView(view).check(matches(not(isDisplayed())))

    private fun getView(view: Int) = onView(withId(view))
}
