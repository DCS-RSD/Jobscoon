package com.example.assignment

import android.app.Dialog
import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat

class CustomDialog {
    companion object
    {
        fun customDialog(context: Context,title:String,description:String): Dialog {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.custom_dialog)
            dialog.window?.setBackgroundDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.dialog_background
                )
            )
            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog.setCancelable(false)
            dialog.window?.attributes?.windowAnimations = R.style.animation
            dialog.findViewById<TextView>(R.id.dialogTitle).text=title
            dialog.findViewById<TextView>(R.id.dialogDesc).text=description
return dialog
        }
    }
}