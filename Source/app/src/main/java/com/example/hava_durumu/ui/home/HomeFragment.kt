package com.example.hava_durumu.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.hava_durumu.R
import com.example.hava_durumu.adapter.CitiesAdapter
import com.example.hava_durumu.api.GenericResult
import com.example.hava_durumu.databinding.FragmentHomeBinding
import com.example.hava_durumu.utils.WeatherProgressDialog
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    private val homeViewModel: HomeViewModel by viewModels()

    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(
            requireActivity()
        )
    }
    private val locationPermissionCode = 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getLocation()

        homeViewModel.weatherCitiesLiveData.observe(viewLifecycleOwner, Observer { cities ->
            when (cities) {
                is GenericResult.Loading -> {
                    WeatherProgressDialog.show(requireContext())
                }
                is GenericResult.Success -> {
                    WeatherProgressDialog.close()
                    cities.data?.toList()?.let {
                        binding.recyclerview.adapter = CitiesAdapter(it) { woeid ->
                            val action =
                                HomeFragmentDirections.actionHomeFragmentToDetailFragment(woeid)
                            findNavController().navigate(action)
                        }
                    }
                }
                is GenericResult.Failure -> {
                    WeatherProgressDialog.close()
                    Toast.makeText(context, "Hata Oluştu", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun getLocation() {
        if ((ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                locationPermissionCode
            )
        } else {
            fusedLocationClient.lastLocation.addOnSuccessListener {
                homeViewModel.getNearByCitiesByLocation(it.latitude.toString(), it.longitude.toString())
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation()
            } else {
                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}