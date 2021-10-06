package com.ngocpv.yourweather.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.yourweather.databinding.WeatherSearchingFragmentBinding

class WeatherSearchingFragment : Fragment() {

    companion object {
        fun newInstance() = WeatherSearchingFragment()
    }
    private lateinit var binding: WeatherSearchingFragmentBinding

    private lateinit var viewViewModel: WeatherSearchingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = WeatherSearchingFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewViewModel = ViewModelProvider(this).get(WeatherSearchingViewModel::class.java)
        // TODO: Use the ViewModel
    }

}