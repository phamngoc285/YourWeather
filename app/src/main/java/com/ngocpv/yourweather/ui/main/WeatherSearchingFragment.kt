package com.ngocpv.yourweather.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yourweather.databinding.WeatherSearchingFragmentBinding
import com.ngocpv.yourweather.adapter.WeatherForecastAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherSearchingFragment : Fragment() {

    companion object {
        fun newInstance() = WeatherSearchingFragment()
    }

    private lateinit var binding: WeatherSearchingFragmentBinding
    private val viewModel: WeatherSearchingViewModel by viewModel()
    private val adapter = WeatherForecastAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = WeatherSearchingFragmentBinding.inflate(inflater)
        binding.searchView.requestFocus()
        binding.searchView.onActionViewExpanded()
        binding.searchBtn.setOnClickListener {
            viewModel.searchWeather(binding.searchView.query.toString())
        }
        binding.rvForecast.layoutManager = LinearLayoutManager(requireContext())
        binding.rvForecast.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onViewResumed()
    }

    private fun observeViewModel(){
        viewModel.weatherForecastLiveData.observe(viewLifecycleOwner){
            adapter.setData(it)
        }
    }

}