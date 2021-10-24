package com.ngocpv.yourweather.viewmodeltest

import com.ngocpv.domain.repository.ResponseHandler
import com.ngocpv.domain.usercase.RefineLocalDateUseCase
import com.ngocpv.domain.usercase.SearchWeatherUseCase
import com.ngocpv.yourweather.model.WeatherInformationUIModel
import com.ngocpv.yourweather.ui.main.WeatherSearchingViewModel
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import androidx.lifecycle.Observer
import com.ngocpv.domain.entity.WeatherInformation
import com.ngocpv.yourweather.TestContextProvider
import com.ngocpv.yourweather.firstValue
import kotlinx.coroutines.flow.flowOf
import org.mockito.Mockito.*


class WeatherSearchingViewModelTest : BaseViewModelTest() {

    private lateinit var viewModel : WeatherSearchingViewModel
    @Mock
    private lateinit var searchWeatherUseCase: SearchWeatherUseCase
    @Mock
    private lateinit var refineLocalDateUseCase: RefineLocalDateUseCase

    @Captor
    private lateinit var weatherInformationArgumentCaptor : ArgumentCaptor<List<WeatherInformationUIModel>>
    @Mock
    private lateinit var weatherInformationObserver : Observer<List<WeatherInformationUIModel>>

    private val mockCityName = "cityName"

    override fun setup() {
        super.setup()
        viewModel = WeatherSearchingViewModel(
            searchWeatherUseCase,
            refineLocalDateUseCase,
            TestContextProvider()
        )

        viewModel.weatherForecastLiveData.observeForever(weatherInformationObserver)
    }


    @Test
    fun test_searchWeather_withLoadingResult() = testCoroutineRule.runBlockingTest {
        `when`(searchWeatherUseCase.invoke(mockCityName)).thenReturn(
            flowOf(ResponseHandler.Loading)
        )
        viewModel.searchWeather(mockCityName)

        verify(weatherInformationObserver).onChanged(weatherInformationArgumentCaptor.capture())

        assert(weatherInformationArgumentCaptor.firstValue.size == 1)
        assert(weatherInformationArgumentCaptor.firstValue.contains(WeatherInformationUIModel.Loading))
    }

    @Test
    fun test_searchWeather_withErrorResult() = testCoroutineRule.runBlockingTest {
        `when`(searchWeatherUseCase.invoke(mockCityName)).thenReturn(
            flowOf(ResponseHandler.Failure())
        )
        viewModel.searchWeather(mockCityName)

        verify(weatherInformationObserver).onChanged(weatherInformationArgumentCaptor.capture())

        assert(weatherInformationArgumentCaptor.firstValue.size == 1)
        assert(weatherInformationArgumentCaptor.firstValue[0] is WeatherInformationUIModel.Error)
    }

    @Test
    fun test_searchWeather_withSuccessResult() = testCoroutineRule.runBlockingTest {
        val mockResult = WeatherInformation(
            WeatherInformation.City("name"),
            listOf(WeatherInformation.Forecast(
                "dateTime",
                WeatherInformation.Weather("main", "description"),
                1,
                WeatherInformation.Temperature(2f,3f),
                4
            ))
        )
        `when`(searchWeatherUseCase.invoke(mockCityName)).thenReturn(
            flowOf(ResponseHandler.Success(mockResult))
        )
        viewModel.searchWeather(mockCityName)

        verify(weatherInformationObserver).onChanged(weatherInformationArgumentCaptor.capture())

        assert(weatherInformationArgumentCaptor.firstValue.size == 1)
        assert(weatherInformationArgumentCaptor.firstValue[0] is WeatherInformationUIModel.Forecast)
        val actualResult = weatherInformationArgumentCaptor.firstValue[0] as WeatherInformationUIModel.Forecast
        assert( actualResult.dateTime == "dateTime"
                && actualResult.weather.main == "main"
                && actualResult.weather.description == "description"
                && actualResult.pressure == 1
                && actualResult.temperature.average == (2f + 3f / 2)
                && actualResult.humidity == 4
        )
    }

    @Test
    fun test_searchWeather_withNullInput() {
        val validationResult = viewModel.validateInputSearchingWeather(null)
        assert(!validationResult.first)
        assert(validationResult.second == "Please enter a city name")
    }

    @Test
    fun test_searchWeather_withBlankInput() {
        val validationResult = viewModel.validateInputSearchingWeather("    ")
        assert(!validationResult.first)
        assert(validationResult.second == "Please enter a city name")
    }

    @Test
    fun test_searchWeather_withSmallInput() {
        val validationResult = viewModel.validateInputSearchingWeather(generateString(MINIMUM_SEARCH_INPUT_CHARACTERS_TEST - 1))
        assert(!validationResult.first)
        assert(validationResult.second == "City name must be longer than ${MINIMUM_SEARCH_INPUT_CHARACTERS_TEST - 1} characters")
    }

    @Test
    fun test_searchWeather_withValidInput() {
        val validationResult = viewModel.validateInputSearchingWeather(generateString(MINIMUM_SEARCH_INPUT_CHARACTERS_TEST))
        assert(validationResult.first)
    }

    private fun generateString(numberOfCharacter : Int) : String {
        var result = ""
        for(i in 0 until numberOfCharacter){
            result += "A"
        }
        return result
    }

    companion object {
        const val MINIMUM_SEARCH_INPUT_CHARACTERS_TEST = 4
    }
}