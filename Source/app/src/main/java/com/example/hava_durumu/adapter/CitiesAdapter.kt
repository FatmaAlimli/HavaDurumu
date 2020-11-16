package com.example.hava_durumu.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hava_durumu.databinding.RowCitiesLayoutBinding
import com.example.hava_durumu.models.CitiesModel

class CitiesAdapter(
    private var dataList: List<CitiesModel>,
    private val onClickItem: (woeid: Int) -> Unit
) : RecyclerView.Adapter<CitiesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CitiesViewHolder(
        RowCitiesLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: CitiesViewHolder, position: Int) {
        holder.bind(dataList[position], onClickItem)
    }

    override fun getItemCount() = dataList.size
}

class CitiesViewHolder(private val binding: RowCitiesLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(citiesModel: CitiesModel, onClickItem: (woeid: Int) -> Unit) {
        binding.citiesModel = citiesModel
        binding.cvItemCardContainer.setOnClickListener { onClickItem(citiesModel.woeid) }
    }
}
