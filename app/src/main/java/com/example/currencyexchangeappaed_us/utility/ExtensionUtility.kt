package com.example.currencyexchangeappaed_us.utility

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.widget.addTextChangedListener
import com.example.currencyexchangeappaed_us.R

public inline fun TextView.addTextChangedListener(
    crossinline beforeTextChanged: (
        text: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) -> Unit = { _, _, _, _ -> },
    crossinline onTextChanged: (
        text: CharSequence?,
        start: Int,
        before: Int,
        count: Int
    ) -> Unit = { _, _, _, _ -> },
    crossinline afterTextChanged: (text: Editable?) -> Unit = {}
): TextWatcher {
    val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanged.invoke(s)
        }

        override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {
            beforeTextChanged.invoke(text, start, count, after)
        }

        override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChanged.invoke(text, start, before, count)
        }
    }
    addTextChangedListener(textWatcher)

    return textWatcher
}

public inline fun TextView.doBeforeTextChanged(
    crossinline action: (
        text: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) -> Unit
): TextWatcher = addTextChangedListener(beforeTextChanged = action)

public inline fun TextView.doOnTextChanged(
    crossinline action: (
        text: CharSequence?,
        start: Int,
        before: Int,
        count: Int
    ) -> Unit
): TextWatcher = addTextChangedListener(onTextChanged = action)

public inline fun TextView.doAfterTextChanged(
    crossinline action: (text: Editable?) -> Unit
): TextWatcher = addTextChangedListener(afterTextChanged = action)


fun Context.color(@ColorRes color: Int) = ContextCompat.getColor(this, color)

fun Activity.setTransparentStatusBar() = window?.apply {
    WindowCompat.setDecorFitsSystemWindows(this, false)
    statusBarColor = color(R.color.transparent)
    navigationBarColor = color(R.color.transparent)
}
