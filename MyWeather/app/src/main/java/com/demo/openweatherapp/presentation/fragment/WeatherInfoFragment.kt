package com.demo.openweatherapp.presentation.fragment

import android.Manifest
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.core.app.ActivityCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.demo.openweatherapp.R
import com.demo.openweatherapp.databinding.FragmentWeatherInfoBinding
import com.demo.openweatherapp.domain.model.WeatherModel
import com.demo.openweatherapp.presentation.util.Constants.EMPTY_STRING
import com.demo.openweatherapp.presentation.util.Constants.IMAGE_URL
import com.demo.openweatherapp.presentation.util.getLastCityName
import com.demo.openweatherapp.presentation.util.storeCityInfo
import com.demo.openweatherapp.presentation.util.viewBinding
import com.demo.openweatherapp.presentation.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class WeatherInfoFragment : Fragment(R.layout.fragment_weather_info), LocationListener {

    private var progressDialog: ProgressDialog? = null
    private var currentLocationCity: String = EMPTY_STRING
    private lateinit var locationManager: LocationManager
    private val weatherViewModel by lazy {
        ViewModelProvider(this)[WeatherViewModel::class.java]
    }
    private val binding by viewBinding(FragmentWeatherInfoBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        fetchCurrentLocation()
        fetchLastUpdatedCityInfo()
        progressDialog = ProgressDialog(requireContext())
        binding.inputCityName.editText?.addTextChangedListener {
            binding.pressMe.isEnabled = it.toString().isNotEmpty()
        }

        weatherViewModel.weatherData.observe(viewLifecycleOwner) {
            progressDialog?.dismiss()
            it?.let {
                updateWeatherInfo(it)
            } ?: showErrorDialog()
        }

        binding.pressMe.setOnClickListener {
            val cityName = binding.inputCityName.editText?.text.toString()
            showProgressDialog()
            weatherViewModel.fetchWeatherData(cityName)
        }
    }

    /**
     * Method to fetch last updated city info
     */
    private fun fetchLastUpdatedCityInfo() {
        val lastUpdatedCityName = getLastCityName(binding.root.context)
        if (lastUpdatedCityName.isNotEmpty()) {
            binding.inputCityName.editText?.setText(lastUpdatedCityName)
            showProgressDialog()
            weatherViewModel.fetchWeatherData(lastUpdatedCityName)
            locationManager.removeUpdates(this)
        }
    }

    /**
     * Method to update Weather Info
     * @param weatherModel : Weather Model
     */
    private fun updateWeatherInfo(weatherModel: WeatherModel) {
        with(weatherModel) {
            val cityName = binding.inputCityName.editText?.text.toString()
            if (cityName.isEmpty()) {
                binding.tvCityInfo.text = "Current City:$currentLocationCity"
            } else {
                storeCityInfo(binding.root.context, cityName)
                binding.tvCityInfo.text = "City:$cityName"
            }
            binding.weatherDescription.text = getString(R.string.weather, weather.description)
            binding.feelsLike.text = getString(R.string.feels_like, main.feels_like.toString())
            binding.tvHumidity.text = getString(R.string.humidity, main.humidity.toString())
            val url = String.format(IMAGE_URL, weather.icon)
            Glide.with(this@WeatherInfoFragment).load(url).into(binding.weatherIcon);
            binding.tempValues.text = String.format(
                getString(
                    R.string.temp_values,
                    main.temp.toString(),
                    main.temp_min.toString(),
                    main.temp_min.toString()
                )
            )
        }
    }

    /**
     * Method to fetch current Location
     */
    private fun fetchCurrentLocation() {
        context?.let { context ->
            if (ActivityCompat.checkSelfPermission(
                    context, Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context, Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 0, 0F, this
                )
                return
            }
        }
    }

    /**
     * Method to display Error Dialog
     */
    private fun showErrorDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.error_dialog_title))
        builder.setMessage(getString(R.string.error_dialog_message))
        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }

    /**
     * Showing progressDialog
     */
    private fun showProgressDialog() {
        progressDialog?.setMessage("fetching weather updates")
        progressDialog?.show()
    }

    override fun onLocationChanged(location: Location) {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        val addresses: List<Address>? =
            geocoder.getFromLocation(location.latitude, location.longitude, 1)
        addresses?.let {
            currentLocationCity = addresses[0].locality
            locationManager.removeUpdates(this)
            weatherViewModel.fetchWeatherData(currentLocationCity)
        }
    }
}