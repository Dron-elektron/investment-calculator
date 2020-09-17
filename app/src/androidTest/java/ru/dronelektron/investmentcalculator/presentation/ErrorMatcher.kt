package ru.dronelektron.investmentcalculator.presentation

import android.view.View
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

class ErrorMatcher(private val expectedErrorResId: Int) : TypeSafeMatcher<View>() {
    override fun describeTo(description: Description?) {
    }

    override fun matchesSafely(item: View?): Boolean {
        if (item !is TextInputLayout) return false

        val expectedError = item.resources.getString(expectedErrorResId)
        val actualError = item.error

        return expectedError == actualError
    }
}
