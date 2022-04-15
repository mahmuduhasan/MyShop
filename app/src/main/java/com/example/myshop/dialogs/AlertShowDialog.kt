package com.example.myshop.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.myshop.R

class AlertShowDialog(
    private val icon : Int = R.drawable.ic_baseline_delete_24,
    private val title : String,
    private val body : String,
    private val positiveText : String,
    private val negativeText : String,
    val positiveButtonClick : () -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity()).apply {
            setIcon(icon)
            setTitle(title)
            setMessage(body)
            setPositiveButton(positiveText) { dialogInterface, i ->
                positiveButtonClick()
            }
            setNegativeButton(negativeText,  null)
        }

        return builder.create()
    }

}