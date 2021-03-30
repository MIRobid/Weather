package com.example.android.weather.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.android.weather.R
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    //private lateinit var viewModel: MainViewModel
    private lateinit var binding: ResultProfileBinding
    private var _binding: ResultProfileBinding?=null
    private val binding get()=_binding!!


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding=ResultProfileBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ResultProfileBinding.inflate(getLayoutInflater())
        val view = binding.getRoot()
        setContentView(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner,Observer{
            renderData(it)})
        viewModel.getWeather()
        //binding.name.setText(viewModel.getName())
        //binding.button.setOnClickListener{viewModel.userClicked()}
    }

    private fun renderData(appSate:AppState){
        when(appState){
            is AppState.Success -> {
                val weatherData = appSate.weatherData
                loadingLayout.visibility=View.GONE
                setData(weatherData)
            }
            is AppState.Loading -> {
                loadingLayout.visibility=View.VISIBLE
            }
            is AppState.Error -> {
                loadingLayout.visibility = View.GONE
                Snackbar
                    .make(mainView,"Error",Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload"){viewModel.getWeather()}
                    .show()
            }
        }
    }

    private fun setData(weatherData:Weather){
        cityName.text=weatherData.city.city
        cityCoordinates.text=String.format(
            getString(R.string.city_coordinates),
            weatherData.city.lat.toString(),
            weatherData.city.lon.toString()
        )
        temperatureValue.text=weatherData.temperature.toString()
        feelsLikeValue.text=weatherData.feelsLike.toString()
    }

    override fun onDestroyView(){
        super.OnDestroyView()
        _binding=null
    }

}