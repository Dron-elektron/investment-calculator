package ru.dronelektron.investmentcalculator.presentation.investform

import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import org.junit.Test
import org.junit.runner.RunWith
import ru.dronelektron.investmentcalculator.R
import ru.dronelektron.investmentcalculator.presentation.MainActivity

@RunWith(AndroidJUnit4::class)
class InvestFormFragmentTest {
    @Test
    fun test_investFormIsVisible() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.fragment_invest_form_root))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_whenBalanceIsEmpty_showsError() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.invest_form_calculate)).perform(click())
        onView(withId(R.id.balance_layout)).check(matches(matchError(R.string.invest_form_error_empty_balance)))
    }

    @Test
    fun test_whenBalanceIsZero_showsError() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.invest_form_balance)).perform(typeText("0"))
        onView(withId(R.id.invest_form_calculate)).perform(click())
        onView(withId(R.id.balance_layout)).check(matches(matchError(R.string.invest_form_error_zero_balance)))
    }

    @Test
    fun test_whenPercentIsEmpty_showsError() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.invest_form_balance)).perform(typeText(DEFAULT_BALANCE))
        onView(withId(R.id.invest_form_calculate)).perform(click())
        onView(withId(R.id.percent_layout)).check(matches(matchError(R.string.invest_form_error_empty_percent)))
    }

    @Test
    fun test_whenPercentIsZero_showsError() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.invest_form_balance)).perform(typeText(DEFAULT_BALANCE))
        onView(withId(R.id.invest_form_percent)).perform(typeText("0"))
        onView(withId(R.id.invest_form_calculate)).perform(click())
        onView(withId(R.id.percent_layout)).check(matches(matchError(R.string.invest_form_error_zero_percent)))
    }

    @Test
    fun test_whenIterationsIsEmpty_showsError() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.invest_form_balance)).perform(typeText(DEFAULT_BALANCE))
        onView(withId(R.id.invest_form_percent)).perform(typeText(DEFAULT_PERCENT))
        onView(withId(R.id.invest_form_calculate)).perform(click())
        onView(withId(R.id.iterations_layout)).check(matches(matchError(R.string.invest_form_error_empty_iterations)))
    }

    @Test
    fun test_whenIterationsIsZero_showsError() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.invest_form_balance)).perform(typeText(DEFAULT_BALANCE))
        onView(withId(R.id.invest_form_percent)).perform(typeText(DEFAULT_PERCENT))
        onView(withId(R.id.invest_form_iterations)).perform(typeText("0"))
        onView(withId(R.id.invest_form_calculate)).perform(click())
        onView(withId(R.id.iterations_layout)).check(matches(matchError(R.string.invest_form_error_zero_iterations)))
    }

    @Test
    fun test_whenAllFieldsAreValid_navigatesToResultsScreen() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.invest_form_balance)).perform(typeText(DEFAULT_BALANCE))
        onView(withId(R.id.invest_form_percent)).perform(typeText(DEFAULT_PERCENT))
        onView(withId(R.id.invest_form_iterations)).perform(typeText(DEFAULT_ITERATIONS))
        onView(withId(R.id.invest_form_calculate)).perform(click())
        onView(withId(R.id.fragment_invest_result_root)).check(matches(isDisplayed()))

        pressBack()

        onView(withId(R.id.fragment_invest_form_root)).check(matches(isDisplayed()))
    }

    private fun matchError(expectedErrorResId: Int) = object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description?) {
        }

        override fun matchesSafely(item: View?): Boolean {
            if (item !is TextInputLayout) return false

            val expectedError = item.resources.getString(expectedErrorResId)
            val actualError = item.error

            return expectedError == actualError
        }
    }

    companion object {
        private const val DEFAULT_BALANCE = "1000"
        private const val DEFAULT_PERCENT = "2.5"
        private const val DEFAULT_ITERATIONS = "8"
    }
}
