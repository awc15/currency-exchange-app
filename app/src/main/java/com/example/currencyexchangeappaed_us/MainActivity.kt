package com.example.currencyexchangeappaed_us

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.currencyexchangeappaed_us.databinding.ActivityMainBinding
import com.example.currencyexchangeappaed_us.utility.doAfterTextChanged
import com.example.currencyexchangeappaed_us.utility.setOnFocusChangedListener
import com.example.currencyexchangeappaed_us.utility.setTransparentStatusBar

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private val conversionRate = 3.6725
    private var isAedFocused = false
    private var isDollarFocused = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding?.let { bind ->
            setTransparentStatusBar()
            setContentView(bind.root)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

            bind.apply {
                etAed.setOnFocusChangedListener { hasFocus ->
                    isAedFocused = hasFocus
                }

                etDollar.setOnFocusChangedListener { hasFocus ->
                    isDollarFocused = hasFocus
                }

                etAed.doAfterTextChanged { aedValue ->
                    if (isAedFocused && aedValue?.isNotBlank() == true) {
                        val formattedValue = formatString(safeParseDouble(aedValue.toString()) / conversionRate)
                        if (etDollar.text.toString() != formattedValue) {
                            etDollar.setText(formattedValue)
                        }
                    }
                }

                etDollar.doAfterTextChanged { usdValue ->
                    if (isDollarFocused && usdValue?.isNotBlank() == true) {
                        val formattedValue = formatString((safeParseDouble(usdValue.toString()) * conversionRate))
                        if (etAed.text.toString() != formattedValue) {
                            etAed.setText(formattedValue)
                        }
                    }
                }

            }
        }
    }
    private fun safeParseDouble(value: String): Double {
        return try {
            // Try to parse the value as a double
            value.toDouble()
        } catch (e: NumberFormatException) {
            // If parsing fails, return 0.0 or handle it as needed
            0.0
        }
    }
    private fun formatString(value: Double): String {
       return String.format("%.2f",value)
    }
}