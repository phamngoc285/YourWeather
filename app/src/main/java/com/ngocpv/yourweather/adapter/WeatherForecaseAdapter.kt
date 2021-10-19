package com.ngocpv.yourweather.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yourweather.R
import com.ngocpv.yourweather.model.WeatherInformationUIModel

class WeatherForecastAdapter : RecyclerView.Adapter<WeatherForecastAdapter.WeatherForecastViewHolder>() {

    companion object{
        const val CELSIUS_STRING = "\u00B0C"
    }

    private var data : List<WeatherInformationUIModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherForecastViewHolder {
        val viewCell = LayoutInflater.from(parent.context)
            .inflate(R.layout.weather_forecast_view_cell, parent, false)
        return WeatherForecastViewHolder(viewCell)
    }

    override fun onBindViewHolder(holder: WeatherForecastViewHolder, position: Int) {
        holder.bindData(getItem(position), isLastPosition(position))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(data : List<WeatherInformationUIModel>) {
        this.data = data
        notifyDataSetChanged()
    }

    private fun getItem(position : Int) = data[position]

    private fun isLastPosition(position: Int) = position == data.size - 1

    class WeatherForecastViewHolder( itemView: View) : RecyclerView.ViewHolder(itemView){
        private val tvMessage : TextView = itemView.findViewById(R.id.tv_Message)
        private val separator : View = itemView.findViewById(R.id.separator)

        fun bindData(data : WeatherInformationUIModel, isLastItem : Boolean){
            separator.visibility = if(isLastItem) View.GONE else View.VISIBLE
            when(data){
                is WeatherInformationUIModel.Forecast -> {
                    tvMessage.text = """
                        Date : ${data.dateTime}
                        Average temperature: ${data.temperature.average} $CELSIUS_STRING
                        Pressure: ${data.pressure}
                        Humidity: ${data.humidity}%
                        Description: ${data.weather.description}
                    """.trimIndent()
                }

                is WeatherInformationUIModel.Loading -> {
                    tvMessage.text = "Loading..."
                }

                is WeatherInformationUIModel.Error -> {
                    tvMessage.text = data.message
                }
            }
        }
    }
}