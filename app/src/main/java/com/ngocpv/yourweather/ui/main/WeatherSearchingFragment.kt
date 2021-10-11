package com.ngocpv.yourweather.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.yourweather.databinding.WeatherSearchingFragmentBinding
import com.ngocpv.domain.entity.WeatherInformation
import com.ngocpv.domain.repository.ResponseHandler
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherSearchingFragment : Fragment() {

    companion object {
        fun newInstance() = WeatherSearchingFragment()
    }

    private lateinit var binding: WeatherSearchingFragmentBinding
    private val viewModel: WeatherSearchingViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = WeatherSearchingFragmentBinding.inflate(inflater)
        binding.searchBtn.setOnClickListener {
            viewModel.searchWeather(binding.searchView.query.toString())
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.resultLiveData.observe(viewLifecycleOwner){
            when(it){
                is ResponseHandler.Success -> displayWeatherResult(it.data)

                is ResponseHandler.Failure -> {
                    binding.message.text = it.error
                    binding.message.visibility = View.VISIBLE
                }

                is ResponseHandler.Loading -> {
                    binding.message.text = "Loading..."
                    binding.message.visibility = View.VISIBLE
                }

                else -> {
                    binding.message.visibility = View.GONE
                }
            }
        }
    }

    private fun displayWeatherResult(weatherInformation: WeatherInformation){
        binding.message.visibility = View.VISIBLE
        binding.message.text = """Here is the weather condition in ${weatherInformation.name}:
        
        Weather: 
            Main : ${weatherInformation.weather.main}
            Desription: ${weatherInformation.weather.description}
        
        Base: ${weatherInformation.base}
        
        Main: 
            Temperature: ${weatherInformation.main.temp}
            Temperature max: ${weatherInformation.main.temp_max}
            Temperature min: ${weatherInformation.main.temp_min}
            Pressure: ${weatherInformation.main.pressure}
            Humidity: ${weatherInformation.main.humidity}
            
        Visibility: ${weatherInformation.visibility}
        
        Wind:
            Speed: ${weatherInformation.wind.speed}
    """

    }

}