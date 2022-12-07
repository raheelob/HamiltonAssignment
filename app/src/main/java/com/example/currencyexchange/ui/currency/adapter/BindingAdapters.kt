package com.example.currencyexchange.ui.currency.adapter

import android.util.Log
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.currencyexchange.R
import com.example.currencyexchange.data.model.ConversionModel


object BindingAdapters {
    @BindingAdapter("imagePath")
    @JvmStatic
    fun imagePath(appCompatImageView: AppCompatImageView, url: String?) {
        url?.let {
            Glide
                .with(appCompatImageView.context)
                .load(it)
                .into(appCompatImageView)
        }
    }

    @BindingAdapter("setFromCurrency")
    @JvmStatic
    fun setFromCurrency(textView: AppCompatTextView, data:ConversionModel?) {
        data?.let {
            textView.text = "${data.currencyToConvert} ${data.currencyToConvertType}"
        }
    }

    @BindingAdapter("setToCurrency")
    @JvmStatic
    fun setToCurrency(textView: AppCompatTextView, data:ConversionModel?) {
        data?.let {
            textView.text = "${data.currencyConverted} ${data.currencyConvertedType}"
        }
    }

    @BindingAdapter("setCurrencyConversion")
    @JvmStatic
    fun setCurrencyConversion(textView: AppCompatTextView, data:ConversionModel?) {
        data?.let {
            textView.text = "You are about to get ${data.currencyConverted} ${data.currencyConvertedType} for ${data.currencyToConvert} ${data.currencyToConvertType}. Do you approve this transaction ?"
        }
    }

    @BindingAdapter("accountCredited")
    @JvmStatic
    fun accountCredited(textView: AppCompatTextView, data:ConversionModel?) {
        data?.let {
            textView.text = "Great now you have ${data.currencyConverted} ${data.currencyConvertedType} in your account"
        }
    }

    @BindingAdapter("yourConversionRate")
    @JvmStatic
    fun yourConversionRate(textView: AppCompatTextView, data:ConversionModel?) {
        data?.let {
            textView.text = "Your conversion rate was 1 / ${"%.4f".format(data.conversionRate)}"
        }
    }
}