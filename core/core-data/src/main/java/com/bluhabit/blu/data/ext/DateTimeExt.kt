/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluhabit.blu.data.ext

import android.util.Log
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun String.toDate(pattern: String): String? {
    return try {
        OffsetDateTime.parse(this).format(DateTimeFormatter.ofPattern(pattern))
    } catch (e: Exception) {
        Log.e("offsetToDate Ext", e.message.toString())
        null
    }
}

fun String.toTimestamp(): Long {
    return try {
        val offsetDateTime = OffsetDateTime.parse(this)
        offsetDateTime.toEpochSecond() * 1000 // Convert to milliseconds
    } catch (e: Exception) {
        e.printStackTrace()
        0 // Return 0 if parsing fails
    }
}

fun LocalDate.localDateToOffset(): String {
    val localDateTime = LocalDateTime.of(this, LocalTime.now())
    val offsetDateTime = OffsetDateTime.of(localDateTime, ZoneOffset.ofHours(-3))
    return offsetDateTime.toString()
}

fun Long.toDateTime(pattern: String): String {
    val date = Date(this)
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.format(date)
}