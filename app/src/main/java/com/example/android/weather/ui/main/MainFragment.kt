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
import com.example.android.weather.databinding.MainFragmentBinding
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    //private lateinit var viewModel: MainViewModel
    private lateinit var viewModel: MainViewModel
    private var _binding: MainFragmentBinding?=null
    private val binding get()=_binding!!


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding=MainFragmentBinding.inflate(inflater,container,false)
        val view = binding.getRoot()
        return view
    }

    /*
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = MainFragmentBinding.inflate(getLayoutInflater())
        val view = binding.getRoot()
        setContentView(view)
    }
     */

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner,Observer{
            renderData(it)})
        viewModel.getWeatherFromLocalSource()
        //binding.name.setText(viewModel.getName())
        //binding.button.setOnClickListener{viewModel.userClicked()}
    }

    private fun renderData(appState:AppState){
        when(appState){
            is AppState.Success -> {
                val weatherData = appState.weatherData
                binding.loadingLayout.visibility=View.GONE
                setData(weatherData)
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility=View.VISIBLE
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar
                    .make(binding.mainView,"Error",Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload"){viewModel.getWeatherFromLocalSource()}
                    .show()
            }
        }
    }

    private fun setData(weatherData:Weather){
        binding.cityName.text=weatherData.city.city
        binding.cityCoordinates.text=String.format(
            getString(R.string.city_coordinates),
            weatherData.city.lat.toString(),
            weatherData.city.lon.toString()
        )
        binding.temperatureValue.text=weatherData.temperature.toString()
        binding.feelsLikeValue.text=weatherData.feelsLike.toString()
    }

    override fun onDestroyView(){
        super.onDestroyView()
        _binding=null
    }

}