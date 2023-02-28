package com.valkotova.wishboxwithcompose.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.DatePicker
import android.widget.FrameLayout
import com.valkotova.wishboxwithcompose.R

class MyDatePicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var datePicker : DatePicker? = null
    init {
        val view = LayoutInflater.from(context).inflate(R.layout.date_picker, this)
        datePicker = view.findViewById<DatePicker>(R.id.date_picker)
    }
}