/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluehabit.core.ui.components.item.itemTask

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bluehabit.core.ui.R
import com.bluehabit.core.ui.components.checkbox.CheckedCircleCheckBox

@Composable
fun BaseItemTask(
    modifier: Modifier = Modifier,
    priority: String = "none",
    iconPriorityTint: Color? = null,
    checked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit = {},
    content: @Composable ColumnScope.() -> Unit = {},
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            CheckedCircleCheckBox(
                checked = checked
            ) {
                onCheckedChange(checked)
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .weight(10f),
            ) {
                content()
            }
            Spacer(modifier = Modifier.weight(1f))

            val priorityIcon = if (priority == "none") {
                R.drawable.outlined_priority_flag
            } else {
                R.drawable.priority_flag
            }

            Icon(
                painter = painterResource(id = priorityIcon),
                tint = iconPriorityTint ?: Color(0xFF98A2B3),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
            )
        }
    }
}