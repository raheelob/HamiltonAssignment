package com.example.currencyexchange.ui.currency.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyexchange.databinding.ItemCurrencyBinding

class CurrencyListAdapter(
    private val mCurrencyItemClickListener: CurrencyItemClickListener,
    private val stocks: List<String>
) : RecyclerView.Adapter<CurrencyListAdapter.CurrencyListViewHolder>() {

    inner class CurrencyListViewHolder(val binding: ItemCurrencyBinding) :
        RecyclerView.ViewHolder(binding.root)

    class CurrencyItemClickListener(val favoriteItemClickListener: (data: String) -> Unit) {
        fun onClick(data: String) = favoriteItemClickListener(data)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CurrencyListViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemCurrencyBinding.inflate(layoutInflater, viewGroup, false)
        return CurrencyListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return stocks.size
    }

    override fun onBindViewHolder(holder: CurrencyListViewHolder, position: Int) {
        val mItem = stocks.get(holder.absoluteAdapterPosition)
        with(holder as CurrencyListViewHolder) {
            with(binding) {
                data = mItem
                currencyItemClickListener = mCurrencyItemClickListener
                executePendingBindings()
            }
        }
    }

}


