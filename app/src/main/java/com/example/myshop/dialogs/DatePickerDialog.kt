package com.example.myshop.dialogs

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.text.SimpleDateFormat
import java.util.*

class DatePickerDialog(val callBack : (String) -> Unit) : DialogFragment(), DatePickerDialog.OnDateSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val date = Calendar.getInstance()
        val year = date.get(Calendar.YEAR)
        val month = date.get(Calendar.MONTH)
        val day = date.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(requireActivity(),this, year,month, day)
    }
    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        val date = Calendar.getInstance()
        date.set(p1,p2,p3)
        val selectedDate = SimpleDateFormat("dd/MM/yyyy")
            .format(Date(date.timeInMillis))
        callBack(selectedDate)
    }
}