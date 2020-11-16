package com.example.hava_durumu.utils

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.example.hava_durumu.R

object WeatherProgressDialog {
    private var dialog: Dialog? = null

    fun show(context: Context) {
        dialog = Dialog(context)
        dialog?.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.progress_layout)
            setCancelable(false)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            show()
        }
    }

    fun close() {
        dialog?.cancel()
    }
}