# Your weather Application

This is an example Android Application which displays a brief information about weather in a particular city.
Information for the weather is gotten from https://openweathermap.org/api

## Patterms & Architecture:
This application follows MVVM patterm with Clean Architecture (presentation, domain, data)

## Libraries & frameworks:
- Presentation: Livedata, View binding.
- API: Retrofit2.
- Database: Room.
- Dependency inversion: Koin.
- Unit test: Mockito, Junit.

## Checklist:
1. Programming language: Kotlin is required, Java is optional [DONE]
2. Design app's architecture (suggest MVVM)                   [DONE]
3. Apply LiveData mechanism                                   [DONE]
4. UI should be looks like in attachment                      [DONE]
5. Write UnitTests                                            [Done with tests for viewmodel]
6. Caching handling (In-mem caching, In-db caching)           [DONE]
