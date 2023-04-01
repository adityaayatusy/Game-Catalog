package com.aditya.dicoding.gamecatalog.core.utils

import android.annotation.SuppressLint
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

fun Float.afterComa(format: String): Float{
    val df = DecimalFormat(format, DecimalFormatSymbols(Locale.US))
    return df.format(this).toFloat()
}

@SuppressLint("SimpleDateFormat")
fun Date.dateFormat(): String {
    val df = SimpleDateFormat("MMM dd, yyyy")
    return df.format(this)
}

fun String.has(text: String): Boolean{
    return this.lowercase().contains(text)
}