package com.example.currencyexchange.utils

import android.content.Context
import android.widget.Toast
import com.example.currencyexchange.utils.dialog.ProgressDialog

fun showToast(context: Context, messageToShow: String) {
    Toast.makeText(context, messageToShow, Toast.LENGTH_SHORT).show()
}

fun showDialog(mDialog: ProgressDialog){
    mDialog.showDialog()
}

fun hideDialog(mDialog: ProgressDialog){
    mDialog.hideDialog()
}
