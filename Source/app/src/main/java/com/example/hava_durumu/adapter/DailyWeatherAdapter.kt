package com.example.hava_durumu.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hava_durumu.databinding.RowDailyWeatherContentLayoutBinding
import com.example.hava_durumu.databinding.RowDailyWeatherHeaderLayoutBinding
import com.example.hava_durumu.models.ConsolidatedWeather
import java.text.SimpleDateFormat
import java.util.*

class DailyWeatherAdapter(
    private var dataList: List<ConsolidatedWeather>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HEADER -> {
                DailyWeatherHeaderViewHolder(
                    RowDailyWeatherHeaderLayoutBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                DailyWeatherContentViewHolder(
                    RowDailyWeatherContentLayoutBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DailyWeatherContentViewHolder -> {
                holder.bind(dataList[position - CONTENT])
            }
        }

    }

    override fun getItemCount() = dataList.size + 1

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> HEADER
            else -> CONTENT
        }
    }

    companion object {
        const val HEADER = 0
        const val CONTENT = 1
    }
}


class DailyWeatherContentViewHolder(private val binding: RowDailyWeatherContentLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SimpleDateFormat")
    var parser = SimpleDateFormat("yyyy-MM-dd")
    var formatter = SimpleDateFormat("EEEE", Locale.ENGLISH)
    fun bind(consolidatedWeather: ConsolidatedWeather) {
        binding.tvDailyWeatherDay.text =
            formatter.format(parser?.parse(consolidatedWeather.applicable_date))

        binding.tvDailyWeatherMinTemp.text =
            consolidatedWeather.min_temp.toString().split(".").first()
        binding.tvDailyWeatherMaxTemp.text =
            consolidatedWeather.max_temp.toString().split(".").first()
        binding.weatherStateAbbr = consolidatedWeather.weather_state_abbr
    }
}

class DailyWeatherHeaderViewHolder(private val binding: RowDailyWeatherHeaderLayoutBinding) :
    RecyclerView.ViewHolder(binding.root)