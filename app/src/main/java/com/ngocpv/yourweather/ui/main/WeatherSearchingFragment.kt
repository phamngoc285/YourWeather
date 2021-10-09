package com.ngocpv.yourweather.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.yourweather.databinding.WeatherSearchingFragmentBinding
import com.ngocpv.domain.entity.WeatherCondition
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
                    binding.message.text = "Error. Try again."
                }

                is ResponseHandler.Loading -> {
                    binding.message.text = "Loading..."
                }
                else -> {
                    binding.message.visibility = View.GONE
                }
            }
        }
    }

    private fun displayWeatherResult(weatherCondition: WeatherCondition){

        binding.message.text = """Here are the weather condition for ${weatherCondition.name}:
        
        Weather: 
            Main : ${weatherCondition.weather.main}
            Desription: ${weatherCondition.weather.description}
        
        Base: ${weatherCondition.base}
        
        Main: 
            Temperature: ${weatherCondition.main.temp}
            Temperature max: ${weatherCondition.main.temp_max}
            Temperature min: ${weatherCondition.main.temp_min}
            Pressure: ${weatherCondition.main.pressure}
            Humidity: ${weatherCondition.main.humidity}
            
        Visibility: ${weatherCondition.visibility}
        
        Wind:
            Speed: ${weatherCondition.wind.speed}
    """

    }

}