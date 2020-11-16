package com.example.hava_durumu.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.hava_durumu.R
import com.example.hava_durumu.adapter.DailyWeatherAdapter
import com.example.hava_durumu.api.GenericResult
import com.example.hava_durumu.databinding.FragmentDetailBinding
import com.example.hava_durumu.utils.WeatherProgressDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    lateinit var binding: FragmentDetailBinding

    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: DetailFragmentArgs by navArgs()
        detailViewModel.getCitiesDailyForecastDetail(args.woeid)

        detailViewModel.citiesDetailLiveData.observe(viewLifecycleOwner, Observer { citiesDetail ->
            when (citiesDetail) {
                is GenericResult.Loading -> {
                    WeatherProgressDialog.show(requireContext())
                }
                is GenericResult.Success -> {
                    WeatherProgressDialog.close()
                    binding.textTitle.text = citiesDetail.data?.title.toString()
                    binding.weatherStateAbbr =
                        citiesDetail.data?.consolidated_weather?.first()?.weather_state_abbr

                    binding.textTemp.text =
                        citiesDetail.data?.consolidated_weather?.first()?.the_temp?.toInt()
                            .toString()

                    binding.textState.text =
                        citiesDetail.data?.consolidated_weather?.first()?.weather_state_name


                    citiesDetail.data?.consolidated_weather?.let {
                        binding.rvCitiestDailyWeather.adapter =
                            DailyWeatherAdapter(it.filterIndexed { index, _ -> index != 0 })
                    }
                }
                is GenericResult.Failure -> {
                    WeatherProgressDialog.close()
                    Toast.makeText(context, "Hata Oluştu", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}
