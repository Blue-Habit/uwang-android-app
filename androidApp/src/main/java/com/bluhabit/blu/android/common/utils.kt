/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluhabit.blu.android.common

import android.content.Context
import android.text.Spanned
import androidx.core.text.HtmlCompat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun loadHtmlFromAssets(context: Context, fileName: String): Spanned {
    val inputStream = context.assets.open(fileName)
    val size = inputStream.available()
    val buffer = ByteArray(size)
    inputStream.read(buffer)
    inputStream.close()

    val htmlContent = String(buffer, Charsets.UTF_8)
    return HtmlCompat.fromHtml(htmlContent, HtmlCompat.FROM_HTML_MODE_LEGACY)
}

fun Long.toDateTime(pattern: String): String {
    val date = Date(this)
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.format(date)
}