package com.example.tasktrek.utils.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date?.getHumanReadableDate(): String {
    if (this == null) {
        return ""
    }

    val simpleDateFormat = SimpleDateFormat("EEE, d MMM yyyy", Locale.ENGLISH)
    return try {
        simpleDateFormat.format(this)
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}