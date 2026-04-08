//
//  ContentView.swift
//  Masala_Dosa
//
//  Created by Shambhavi Verma on 07/04/26.
//
import UIKit
import SwiftUI
import AVFoundation

struct ContentView: View {
    @State private var username: String = ""
    @State private var password: String = ""
    @State private var showError: Bool = false
    @State private var isLoggedIn: Bool = false
    @State private var audioPlayer: AVAudioPlayer?
    
    var body: some View {
        
        NavigationStack {
            ZStack {
                Image("cloud")
                    .resizable()
                    .scaledToFill()
                    .ignoresSafeArea()
                
                VStack(spacing: 20) {

                    Text("Between sun and storm.")
                        .font(.subheadline)
                        .fontWeight(.semibold)
                        .foregroundColor(.pink)

                    Image("Image 1")
                        .resizable()
                        .scaledToFill()
                        .frame(width: 130, height: 130)
                        .clipShape(Circle())
                        .shadow(radius: 15)
                        .padding(.top, 30)

                    Text("CRONOS")
                        .font(.system(size: 42, weight: .semibold, design: .serif))
                        .padding(.vertical, 8)
                        .frame(maxWidth: 220)
                        .background(
                            RoundedRectangle(cornerRadius: 16)
                                .fill(Color.pink.opacity(0.6))
                        )

                    VStack(spacing: 16) {

                        Text("USER LOGIN")
                            .font(.headline)
                            .foregroundColor(.pink)

                        HStack {
                            Image(systemName: "person.fill")
                                .foregroundColor(.blue)
                                .frame(width: 20)

                            TextField("Username", text: $username)
                                .padding()
                                .background(Color.white.opacity(0.2))
                                .cornerRadius(10)
                        }

                        HStack {
                            Image(systemName: "lock.fill")
                                .foregroundColor(.blue)
                                .frame(width: 20)

                            SecureField("Password", text: $password)
                                .padding()
                                .background(Color.white.opacity(0.2))
                                .cornerRadius(10)
                        }

                        if showError {
                            Text("Please enter username and password")
                                .font(.caption)
                                .foregroundColor(.red)
                        }

                        Button("Forgot Password?") {}
                            .font(.caption)
                            .foregroundColor(.blue)

                        Button(action: loginUser) {
                            Text("Login")
                                .font(.headline)
                                .frame(maxWidth: .infinity)
                                .padding()
                                .background(Color.green)
                                .foregroundColor(.white)
                                .cornerRadius(12)
                        }
                    }
                    .padding()
                    .background(
                        RoundedRectangle(cornerRadius: 20)
                            .fill(Color.white.opacity(0.7))
                            .shadow(radius: 10)
                    )
                    .padding(.horizontal)

                    Spacer()

                    Button("HELP(touch me pls)") {
                        playSound(sound: "fahhhhh", type: "mp3")
                    }
                    .foregroundColor(.red)
                    .padding(.bottom, 30)
                }
            }
            .navigationDestination(isPresented: $isLoggedIn) {
                ContentView2()
            }
        }
    }

    func loginUser() {
        if username.isEmpty {
            showError = true
        } else {
            showError = false
            isLoggedIn = true
        }
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
    ContentView()
}
