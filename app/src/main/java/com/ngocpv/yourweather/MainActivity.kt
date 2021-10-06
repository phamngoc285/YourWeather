package com.ngocpv.yourweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.yourweather.R
import com.ngocpv.yourweather.ui.main.WeatherSearchingFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, WeatherSearchingFragment.newInstance())
                .commitNow()
        }
    }
}