/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluehabit.budgetku.android.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.bluehabit.budgetku.android.R
import com.bluehabit.budgetku.android.ui.BudgetKuTheme

enum class Gender(val value: String, val label: Int) {
    MALE(value = "L", label = R.string.male),
    FEMALE(value = "P", label = R.string.female)
}


@Composable
fun BottomSheetGenderPicker(
    title: String = "Pilih Jenis Kelamin Kamu",
    genders: List<Gender> = listOf(Gender.MALE, Gender.FEMALE),
    selectedGender: Gender? = null,
    onDismiss: () -> Unit = {},
    onConfirm: (Gender) -> Unit = {}
) {
    var selected by remember {
        mutableStateOf(selectedGender)
    }
    BaseBottomSheet(
        onDismiss = onDismiss,
        onConfirm = {
            selected?.let {
                onConfirm(it)
            }
        },
        textConfirmation = "Pilih",
        enableConfirmation = selected != null
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        genders.forEachIndexed { index, gender ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = gender.label),
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Normal,
                    color = if (selected?.value == gender.value) MaterialTheme.colors.onSurface else Color.LightGray
                )
                if (selected?.value == gender.value) {
                    Icon(
                        imageVector = Icons.Outlined.Check,
                        contentDescription = "",
                        tint = MaterialTheme.colors.primary
                    )
                }
            }
            if (index != genders.size - 1) {
                Divider()
            }
        }

    }
}

@Preview
@Composable
fun PreviewBottomSheetGenderPicker() {
    BudgetKuTheme {
        BottomSheetGenderPicker()
    }
}