

//  ContentView3.swift
//  Masala_Dosa
//
//  Created by Kartikey Chaudhary on 08/04/26.
//

import UIKit
import SwiftUI
import AVFoundation

struct ContentView3: View {
    @State private var meettech: Bool = false
    @State private var audioPlayer: AVAudioPlayer?
    
    var temperature: Double
    var weatherCode: Int
    
    var body: some View {
        ZStack {
            Image("Image 2")
                .resizable() .scaledToFill()
                .ignoresSafeArea()
                .ignoresSafeArea()
            
            VStack(spacing: 40) {
                Text("WARNING!")
                    .font(.system(size: 36, weight: .semibold))
                    .foregroundColor(.green)
                    .padding(.top, 40)
                
                
                VStack(spacing: 25) {
                    
                    Text(getSuggestionText(code: weatherCode, temp: temperature))
                        .font(.title3)
                        .fontWeight(.medium)
                        .foregroundColor(.white)
                        .multilineTextAlignment(.center)
                        .lineSpacing(6)
                    
                    Button("call our tech support📞🥺"){
                        playSound(sound: "TechSupportCalling2", type: "mp3")
                    }
                }
                
               
                        .padding(30)
                                              RoundedRectangle(cornerRadius: 25)
                        .fill(Color.red.opacity(0.6))
                        .shadow(color: .purple, radius: 20)
                        .cornerRadius(20)
                        .padding(.horizontal, 20)
                    
                    Spacer()
                
            }
        }}
        
        
        private func getSuggestionText(code: Int, temp: Double) -> String {
            var suggestion = ""
            
            
            switch code {
            case 0...3:
                suggestion = "The skies are looking mostly clear! "
            case 45, 48:
                return "It's quite foggy out there. Drive safely and keep your headlights on!"
            case 51...65, 80...82:
                return "It's wet outside. Don't forget to grab an umbrella or a raincoat before heading out!"
            case 71...75:
                return "Snow is expected! Dress warmly in layers and wear appropriate footwear."
            case 95...99:
                return "Thunderstorms ahead. It's best to stay indoors if possible and avoid open areas."
            default:
                suggestion = "Have a great day ahead! "
            }
            
            
            if temp < 10 {
                suggestion += "It's quite cold today, so make sure to bundle up."
            } else if temp > 25 {
                suggestion += "It's warm out, so stay hydrated and wear breathable clothing."
            } else {
                suggestion += "The temperature is very comfortable right now."
            }
            
            return suggestion
        }
        func playSound(sound: String, type: String) {
            if let path = Bundle.main.path(forResource: sound, ofType: type) {
                do {
                    audioPlayer = try AVAudioPlayer(contentsOf: URL(fileURLWithPath: path))
                    audioPlayer?.play()
                } catch {
                    print("Could not find or play the file")
                }
            }
        }
    }

#Preview {
    ContentView3(temperature: 28.0, weatherCode: 1)
}
