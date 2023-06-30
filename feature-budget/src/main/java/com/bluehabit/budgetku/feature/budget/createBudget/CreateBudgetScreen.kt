/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluehabit.budgetku.feature.budget.createBudget

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.trian.mvi.Navigation
import app.trian.mvi.ui.UIWrapper
import app.trian.mvi.ui.internal.UIContract
import app.trian.mvi.ui.internal.listener.BaseEventListener
import app.trian.mvi.ui.internal.listener.EventListener
import com.bluehabit.budgetku.feature.budget.createBudget.components.ScreenInputAmountBudget
import com.bluehabit.core.ui.BaseMainApp
import com.bluehabit.core.ui.BaseScreen
import com.bluehabit.core.ui.components.ScreenNumPad
import com.bluehabit.core.ui.components.bottomSheet.BottomSheetConfirmation
import com.bluehabit.core.ui.routes.Routes

@Navigation(
    route=Routes.CreateBudget.routeName,
    viewModel=CreateBudgetViewModel::class
)
@Composable
fun CreateBudgetScreen(
    uiContract: UIContract<CreateBudgetState, CreateBudgetIntent, CreateBudgetAction>,
    event: BaseEventListener = EventListener(),
) = UIWrapper(uiContract) {


    fun onBackPressed() {
        if (state.step == 1) {
           // showBottomSheet()
        }
        if (state.step > 1) {
            dispatch(CreateBudgetAction.Prev)
        }
    }
    BackHandler {
        onBackPressed()
    }
    BaseScreen(
        topAppBar = {
            when (state.step) {
                1 -> {
                    TopAppbarCreateBudget {
                        navigator.navigateUp()
                    }
                }

                else -> Unit
            }
        },
        bottomSheet = {
            BottomSheetConfirmation(
                title = "Yakin membatalkan perubahan?",
                message = "Data yang sudah kamu ubah belum tersimpan dan akan hilang.",
                textConfirmation = "Yakin",
                textCancel = "Batal",
                onDismiss = {
                    //hideBottomSheet()
                },
                onConfirm = {
                    //hideBottomSheet()
                    navigator.navigateUp()
                }
            )
        }
    ) {
        when (state.step) {
            1 -> {
                ScreenInputAmountBudget(
                    amount = state.nominal,
                    onInputAmount = {
                        dispatch(CreateBudgetAction.Next)
                    },
                    onSubmit = {
                       navigator.navigateAndReplace(Routes.ResultCreateBudget.routeName)
                    }
                )
            }

            2 -> {
                ScreenNumPad(
                    value = state.nominal,
                    onChange = {
                        dispatch(CreateBudgetAction.Input(it))
                    },
                    onSubmit = {
                        dispatch(CreateBudgetAction.Prev)

                    },
                    onClear = {
                        dispatch(CreateBudgetAction.Clear)
                    },
                    onRemove = {
                        dispatch(CreateBudgetAction.Remove)
                    },
                    onDismiss = {
                        dispatch(CreateBudgetAction.Prev)
                    }
                )
            }
        }
    }


}

@Composable
fun TopAppbarCreateBudget(
    onBackPressed: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)
            .padding(
                vertical = 20.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onBackPressed) {
            Icon(
                imageVector = Icons.Outlined.ArrowBack,
                contentDescription = "",
                tint = MaterialTheme.colors.surface
            )
        }
        Text(
            text = "Atur Total Budget",
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.surface
        )
        Spacer(modifier = Modifier.width(8.dp))
    }
}


@Preview
@Composable
fun PreviewScreenCreateBudget() {
    BaseMainApp(
    ) {
        CreateBudgetScreen(
            UIContract(
                controller = it,
                state = CreateBudgetState()
            )
        )
    }
}