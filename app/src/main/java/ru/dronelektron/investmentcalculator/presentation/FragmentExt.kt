package ru.dronelektron.investmentcalculator.presentation

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

fun Fragment.closeKeyboard() {
    activity?.let {
        val view = it.currentFocus ?: return
        val inputMethodManager = it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
