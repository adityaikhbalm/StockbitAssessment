package com.stockbit.features.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stockbit.common.extension.colorStateList
import com.stockbit.common.extension.getColorCompat
import com.stockbit.features.home.R
import com.stockbit.features.home.databinding.ItemHomeBinding
import com.stockbit.model.CoinInfo
import com.stockbit.model.CoinModel
import com.stockbit.model.TickerModel

class HomeAdapter : ListAdapter<CoinInfo, HomeAdapter.Holder>(CoinDiffUtil()) {

    var openDay = mutableMapOf<String, Double>()
    var lastPrice = mutableMapOf<String, Double>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.Holder {
        val binding = ItemHomeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: HomeAdapter.Holder, position: Int) {
        holder.bind(getItem(position).coinInfo)
    }

    fun updatePrice(tickerModel: TickerModel) {
        currentList.find { it.coinInfo.name == tickerModel.symbol }?.run {
            if (tickerModel.price > 0) coinInfo.price = tickerModel.price
            if (tickerModel.volume > 0) coinInfo.volume = tickerModel.volume
            notifyDataSetChanged()
        }
    }

    @SuppressLint("SetTextI18n")
    inner class Holder(
        private val binding: ItemHomeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(coinModel: CoinModel) {
            binding.run {
                binding.textName.text = coinModel.name
                binding.textFullName.text = "(${coinModel.fullName})"

                binding.textVolume.text = "Vol ${volumeViews(coinModel.volume)}"

                lastPrice[coinModel.name]?.let {
                    val color = when {
                        coinModel.price > it -> R.color.colorGreen
                        coinModel.price == it -> R.color.textColorActive
                        else -> R.color.colorRed
                    }
                    changeTextColor(color, coinModel.price)
                } ?: changeTextColor(R.color.textColorActive, coinModel.price)

                coinModel.name?.let {
                    lastPrice[it] = coinModel.price
                }

                val open = openDay[coinModel.name]
                if (open != null && coinModel.price > 0) {
                    val percent = (coinModel.price / open) * 100 - 100
                    val color: Int
                    var symbol = ""
                    when {
                        percent > 0 -> {
                            color = R.color.colorGreen
                            symbol = "+"
                        }
                        percent == 0.0 -> color = R.color.shimmerCard
                        else -> color = R.color.colorRed
                    }
                    changeCardColor(color, percent, symbol)
                }
            }
        }

        private fun changeTextColor(color: Int, price: Double) {
            binding.textPrice.setTextColor(itemView.context.getColorCompat(color))
            binding.textPrice.text = "$$price"
        }

        private fun changeCardColor(color: Int, percent: Double, symbol: String) {
            itemView.context.colorStateList(color)?.let {
                binding.cardPercent.setCardBackgroundColor(it)
            }
            binding.textPercent.text = symbol + String.format("%,.2f", percent) + "%"
        }

        private fun volumeViews(volume : Double) : String {
            return if (volume > 1000000000.0) String.format("%,.2fB", volume / 1000000000.0)
            else if (volume > 1000000.0 && volume < 1000000000.0) String.format("%,.2fM", volume / 1000000.0)
            else String.format("%,.2f", volume)
        }
    }

    class CoinDiffUtil : DiffUtil.ItemCallback<CoinInfo>() {
        override fun areItemsTheSame(
            oldItem: CoinInfo,
            newItem: CoinInfo
        ): Boolean = oldItem.coinInfo.id == newItem.coinInfo.id

        override fun areContentsTheSame(
            oldItem: CoinInfo,
            newItem: CoinInfo
        ): Boolean = oldItem == newItem
    }
}