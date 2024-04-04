/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluhabit.blu.data.ext

@Suppress("UNCHECKED_CAST")
fun <T> Any?.toListOfType(clazz: Class<T>): List<T>? {
    return when (this) {
        is List<*> -> {
            if (isEmpty()) {
                listOf()
            } else {
                val firstItem = firstOrNull()
                if (clazz.isInstance(firstItem)) {
                    this as List<T>
                } else {
                    null // Tipe data list tidak sesuai
                }
            }
        }
        else -> null // Objek Any bukan sebuah List
    }
}