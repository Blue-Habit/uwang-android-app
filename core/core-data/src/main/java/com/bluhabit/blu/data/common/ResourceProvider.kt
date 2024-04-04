/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluhabit.blu.data.common

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ResourceProvider(
    private val appContext: Context
) {
    fun getString(id: Int): String = appContext.getString(id)
    fun isBadWord(word: String): Boolean {
        val jsonContent = appContext.assets.open("badWord.json").use {
            it.reader().use { reader ->
                reader.readText()
            }
        }
        val badWordList: List<String> = Gson().fromJson(
            jsonContent, object : TypeToken<List<String>>() {}.type
        )
        for (badWord in badWordList) {
            if (word.lowercase().contains(badWord)) {
                return true
            }
        }
        return false
    }
    fun getStringArray(id: Int): Array<String> = appContext.resources.getStringArray(id)
}