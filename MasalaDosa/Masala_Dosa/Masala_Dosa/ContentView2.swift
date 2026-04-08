//
//  ContentView2.swift
//  Masala_Dosa
//
//  Created by Shambhavi Verma on 07/04/26.
//

import SwiftUI

struct GeocodingResponse: Codable {
    let results: [Location]?
}

struct Location: Codable {
    let name: String
    let latitude: Double
    let longitude: Double
}

struct WeatherResponse: Codable {
    let current_weather: CurrentWeather
    let hourly: HourlyWeather
    let daily: DailyWeather
}

struct CurrentWeather: Codable {
    let temperature: Double
    let weathercode: Int
}

struct HourlyWeather: Codable {
    let time: [String]
    let temperature_2m: [Double]
    let weathercode: [Int]
}

struct DailyWeather: Codable {
    let time: [String]
    let weathercode: [Int]
    let temperature_2m_max: [Double]
    let temperature_2m_min: [Double]
}

@MainActor
class WeatherViewModel: ObservableObject {
    @Published var weather: WeatherResponse?
    @Published var cityName: String = "Lucknow"
    @Published var isLoading: Bool = false
    @Published var errorMessage: String? = nil
    
    func fetchWeather(for city: String) async {
        isLoading = true
        errorMessage = nil
        
        do {
            let geoUrlString = "https://geocoding-api.open-meteo.com/v1/search?name=\(city.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed) ?? "")&count=1"
            guard let geoUrl = URL(string: geoUrlString) else { throw URLError(.badURL) }
            
            let (geoData, _) = try await URLSession.shared.data(from: geoUrl)
            let geoResponse = try JSONDecoder().decode(GeocodingResponse.self, from: geoData)
            
            guard let location = geoResponse.results?.first else {
                errorMessage = "City not found"
                isLoading = false
                return
            }
            
            DispatchQueue.main.async {
                self.cityName = location.name
            }
            
            let weatherUrlString = "https://api.open-meteo.com/v1/forecast?latitude=\(location.latitude)&longitude=\(location.longitude)&current_weather=true&hourly=temperature_2m,weathercode&daily=weathercode,temperature_2m_max,temperature_2m_min&timezone=auto"
            guard let weatherUrl = URL(string: weatherUrlString) else { throw URLError(.badURL) }
            
            let (weatherData, _) = try await URLSession.shared.data(from: weatherUrl)
            let weatherResponse = try JSONDecoder().decode(WeatherResponse.self, from: weatherData)
            
            self.weather = weatherResponse
            
        } catch {
            self.errorMessage = "Failed to fetch data."}
        isLoading = false}
    
    
    func formatTime(isoString: String) -> String {
        let formatter = ISO8601DateFormatter()
        formatter.formatOptions = [.withInternetDateTime, .withFractionalSeconds]
       
        _ = isoString + ":00Z"
        
        let customFormatter = DateFormatter()
        customFormatter.dateFormat = "yyyy-MM-dd'T'HH:mm"
        
        guard let date = customFormatter.date(from: isoString) else { return "00:00" }
        
        let displayFormatter = DateFormatter()
        displayFormatter.dateFormat = "h a"
        return displayFormatter.string(from: date)
    }
    
    func formatDay(isoString: String) -> String {
        let customFormatter = DateFormatter()
        customFormatter.dateFormat = "yyyy-MM-dd"
        
        guard let date = customFormatter.date(from: isoString) else { return "Day" }
        
        if Calendar.current.isDateInToday(date) { return "Today" }
        
        let displayFormatter = DateFormatter()
        displayFormatter.dateFormat = "EEE"
        return displayFormatter.string(from: date)
    }
}

struct ContentView2: View {
    @StateObject private var viewModel = WeatherViewModel()
    @State private var searchCity: String = ""
    
    var body: some View {
        
        NavigationStack{
            ZStack {
                Image("Image 3")
                    .resizable() .scaledToFit()
                
                    .ignoresSafeArea()
                
                VStack {
                    
                    HStack {
                        Image(systemName: "magnifyingglass")
                            .foregroundColor(.red.opacity(0.7))
                        TextField("Search for a STATE...`", text: $searchCity)
                            .foregroundColor(.black)
                            .onSubmit {
                                Task { await viewModel.fetchWeather(for: searchCity) }
                            }
                    }
                    .padding()
                    .background(Color.purple)
                    .padding(.horizontal)
                    
                    if viewModel.isLoading {
                        Spacer()
                        ProgressView()
                            .progressViewStyle(CircularProgressViewStyle(tint: .white))
                            .scaleEffect(1.5)
                        Spacer()
                    } else if let errorMessage = viewModel.errorMessage {
                        Spacer()
                        Text(errorMessage).foregroundColor(.white)
                        Spacer()
                    } else if let weather = viewModel.weather {
                        ScrollView(showsIndicators: false) {
                            VStack(spacing: 20) {
                                VStack(spacing: 5) {
                                    Text(viewModel.cityName)
                                        .font(.system(size: 36, weight: .regular))
                                        .foregroundColor(.black)
                                    
                                    Text("\(Int(weather.current_weather.temperature))°")
                                        .font(.system(size: 96, weight: .thin))
                                        .foregroundColor(.white)
                                    
                                    Text(getWeatherDescription(code: weather.current_weather.weathercode))
                                        .font(.title3)
                                        .foregroundColor(.white)
                                    
                                    HStack(spacing: 15) {
                                        Text("Lowest:\(Int(weather.daily.temperature_2m_max.first ?? 0))°")
                                        Text("Highest:\(Int(weather.daily.temperature_2m_min.first ?? 0))°")
                                    }
                                    .font(.title3)
                                    .foregroundColor(.white)
                                }
                                .padding(.top, 20)
                                
                                VStack(alignment: .leading) {
                                    Text("HOURLY FORECAST")
                                        .font(.caption)
                                        .foregroundColor(.red.opacity(0.6))
                                        .padding(.bottom, 5)
                                    
                                    ScrollView(.horizontal, showsIndicators: false) {
                                        HStack(spacing: 25) {
                                            
                                            ForEach(0..<min(24, weather.hourly.time.count), id: \.self) { index in
                                                VStack(spacing: 15) {
                                                    Text(index == 0 ? "Now" : viewModel.formatTime(isoString: weather.hourly.time[index]))
                                                        .font(.subheadline)
                                                        .foregroundColor(.white)
                                                    
                                                    Image(systemName: getWeatherIcon(code: weather.hourly.weathercode[index]))
                                                        .symbolRenderingMode(.multicolor)
                                                        .font(.title2)
                                                    
                                                    Text("\(Int(weather.hourly.temperature_2m[index]))°")
                                                        .font(.headline)
                                                        .foregroundColor(.white)
                                                }}}}}
                                .frame(width: 400, height:80,)
                                .padding()
                                .background(
                                    RoundedRectangle(cornerRadius: 25)
                                        .fill(Color.white.opacity(0.6))
                                        .shadow(color: .purple, radius: 20))
                                .cornerRadius(10)
                                .padding(.horizontal)
                                
                                VStack(alignment: .leading) {
                                    HStack {
                                        Image(systemName: "calendar")
                                        Text("7-DAY FORECAST")
                                    }
                                    .font(.caption)
                                    .foregroundColor(.black.opacity(0.4))
                                    .padding(.bottom, 10)
                                    
                                    ForEach(0..<min(7, weather.daily.time.count), id: \.self) { index in
                                        HStack {
                                            Text(viewModel.formatDay(isoString: weather.daily.time[index]))
                                                .font(.title3)
                                                .foregroundColor(.black)
                                                .frame(width: 60, alignment: .leading)
                                            
                                            Spacer()
                                            
                                            Image(systemName: getWeatherIcon(code: weather.daily.weathercode[index]))
                                                .symbolRenderingMode(.multicolor)
                                                .font(.title2)
                                            
                                            Spacer()
                                            
                                            Text("\(Int(weather.daily.temperature_2m_min[index]))°")
                                                .foregroundColor(.white.opacity(0.6))
                                            
                                            
                                            Capsule()
                                                .fill(LinearGradient(colors: [.cyan, .yellow], startPoint: .leading, endPoint: .trailing))
                                                .frame(width: 80, height: 5)
                                            
                                            Text("\(Int(weather.daily.temperature_2m_max[index]))°")
                                                .foregroundColor(.pink)
                                                .frame(width: 35, alignment: .trailing)
                                        }
                                        .padding(.vertical, 8)
                                        
                                        if index < 6 {
                                            Divider().background(Color.red.opacity(0.3))
                                        }
                                    }
                                }
                                .padding()
                                .background(
                                    RoundedRectangle(cornerRadius: 25)
                                        .fill(Color.red.opacity(0.6))
                                        .shadow(color: .purple, radius: 20))
                                .cornerRadius(10)
                                .padding(.horizontal)
                                
                            }}}
                    if let weather = viewModel.weather {
                        NavigationLink(destination: ContentView3(
                            temperature: weather.current_weather.temperature,
                            weatherCode: weather.current_weather.weathercode
                        )) {
                            Text("Get Today's Suggestion")
                                .font(.headline)
                                .foregroundColor(.white)
                                .frame(maxWidth: .infinity)
                                .padding()
                                .background(Color.blue.opacity(0.6))
                                .cornerRadius(15)
                                .padding(.horizontal)
                                .padding(.bottom, 20)
                        }
                    }
                }
            }}
        
        .task {
            await viewModel.fetchWeather(for: searchCity.isEmpty ? "Lucknow" : searchCity)
        }}
    private func getWeatherDescription(code: Int) -> String {
        switch code {
        case 0: return "Clear Sky"
        case 1...3: return "Partly Cloudy"
        case 45, 48: return "Fog"
        case 51...55: return "Drizzle"
        case 61...65: return "Rain"
        case 71...75: return "Snow"
        case 80...82: return "Rain Showers"
        case 95...99: return "Thunderstorm"
        default: return "Unknown"
        }
    }
    
    private func getWeatherIcon(code: Int) -> String {
        switch code {
        case 0: return "sun.max.fill"
        case 1...3: return "cloud.bolt.rain.fill"
        case 45, 48: return "cloud.heavyrain.fill"
        case 51...55: return "cloud.snow.fill"
        case 61...65: return "cloud.rain.fill"
        case 71...75: return "cloud.drizzle.fill"
        case 80...82: return "cloud.fog.fill"
        case 95...99: return "cloud.sun.fill"
        default: return "cloud.fill"
        }
    }
    
}
#Preview {
    ContentView()
}
