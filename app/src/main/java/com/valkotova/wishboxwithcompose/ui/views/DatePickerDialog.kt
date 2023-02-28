package com.valkotova.wishboxwithcompose.ui.views

import android.app.DatePickerDialog
import android.util.AttributeSet
import android.widget.CalendarView
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.valkotova.wishboxwithcompose.R
import com.valkotova.wishboxwithcompose.ui.views.buttons.BlueButton
import kotlinx.coroutines.launch
import java.util.Calendar

@Composable
fun DatePickerDialog(
      currentDate : Long,
      onPick : (Long) -> Unit
){

    val datePicker : MutableState<DatePicker?> = remember { mutableStateOf(null) }

    Column(
        modifier = Modifier
            .defaultMinSize(minHeight = 1.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        AndroidView(
            {
                MyDatePicker(it).apply {
                    this.datePicker?.let{
                        datePicker.value = it
                        val calendar = Calendar.getInstance()
                        calendar.setTimeInMillis(currentDate)
                        it.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                    }
                }
            },
            modifier = Modifier.wrapContentWidth(),
            update = { views ->

            }
        )
        BlueButton(
            text = stringResource(id = R.string.save_changes),
            onClick = {
                datePicker.value?.let{
                    val calendar = Calendar.getInstance()
                    calendar.set(Calendar.YEAR, it.year)
                    calendar.set(Calendar.MONTH, it.month)
                    calendar.set(Calendar.DAY_OF_MONTH, it.dayOfMonth)
                    onPick(calendar.timeInMillis)
                }
            })
    }
}