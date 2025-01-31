
//
//  WeatherViewModel.swift
//  WeatherApp
//
//  Created by Sandaru Vithanage on 2024-12-24.
//
import Foundation

@MainActor
class WeatherViewModel: ObservableObject {
    @Published var cityName: String = "Sri Lanka"
    @Published var temperature: Double = 30.0
    @Published var condition: String = "Mostly Cloudy"
    @Published var high: Double = 32.0
    @Published var low: Double = 25.0
    @Published var feelsLike: Double = 32.0
    @Published var uvIndex: Double = 7.0
    @Published var windSpeed: Double = 3.0
    @Published var windGust: Double = 14.0
    @Published var windDirection: Int = 19
    @Published var sunrise: Int = 0
    @Published var sunset: Int = 0
    @Published var precipitation: Double = 0.0
    @Published var visibility: Double = 10.0
    @Published var humidity: Int = 70
    @Published var pressure: Int = 1012
    @Published var moonPhase: Double = 0.0

    @Published var hourlyForecast: [HourlyWeather] = []
    @Published var dailyForecast: [DailyWeather] = []

    @Published var errorMessage: String? = nil
    @Published var isLoading: Bool = false

    func fetchWeather(for city: String) async {
        isLoading = true
        errorMessage = nil
        
        do {
            print("Fetching location for city: \(city)")
            let locations = try await GeoService().fetchLocation(for: city)
            
            guard let location = locations.first else {
                errorMessage = "No location data found for \(city)."
                print(errorMessage ?? "")
                isLoading = false
                return
            }
            
            print("Fetching weather data for lat: \(location.lat), lon: \(location.lon)")
            let weather = try await WeatherService().fetchWeather(lat: location.lat, lon: location.lon)
            
            updateUI(with: weather, locationName: location.name)
            print("Weather data updated successfully")
        } catch {
            errorMessage = "Failed to fetch data: \(error.localizedDescription)"
            print(errorMessage ?? "")
        }
        
        isLoading = false
    }

    private func updateUI(with weather: WeatherModel, locationName: String) {
        cityName = locationName
        temperature = weather.current.temp
        condition = weather.current.weather.first?.description ?? "N/A"
        high = weather.daily.first?.temp.max ?? 0.0
        low = weather.daily.first?.temp.min ?? 0.0
        feelsLike = weather.current.feelsLike
        uvIndex = weather.current.uvi
        windSpeed = weather.current.windSpeed
        windGust = weather.hourly.first?.windGust ?? 0.0
        windDirection = weather.current.windDeg

        // Simplified optional unwrapping
        sunrise = weather.daily.first?.sunrise ?? 0
        sunset = weather.daily.first?.sunset ?? 0

        precipitation = weather.daily.first?.rain ?? 0.0
        visibility = Double(weather.current.visibility) / 1000.0 // Convert meters to kilometers
        humidity = weather.current.humidity
        pressure = weather.current.pressure
        moonPhase = weather.daily.first?.moonPhase ?? 0.0
        hourlyForecast = weather.hourly
        dailyForecast = weather.daily
    }
}
