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
                    .resizable() .scaledToFill()
                    .ignoresSafeArea()
                
                VStack (spacing:10){
                    Text("Between sun and storm.")
                        .font(.title3)
                        .fontWeight(.semibold)
                        .bold()
                        .foregroundColor(.pink)
                    
                    Image("Image 1")
                        .resizable() .scaledToFill()
                        .frame(width: 150, height: 150)
                        .clipShape(Circle())
                        .shadow(color: .white, radius: 50)
                        .padding(.top, 60)
                        .padding(.horizontal)
                    Text("CRONOS")
                        .font(.system(size: 48, weight: .medium , design: .serif))
                        .frame(width: 230, height: 45)
                        .background(
                            RoundedRectangle(cornerRadius: 21)
                                .fill(Color.pink.opacity(0.7))
                                .shadow(color: .white, radius: 10)
                            
                                .foregroundColor(.black.opacity(0.7)))
                        .padding(.bottom, 20)
                    Button(action: loginUser) {
                        Text("Login")
                            .font(.headline)
                            .frame(maxWidth: .infinity)
                            .padding()
                            .background(Color (#colorLiteral(red: 0.3303911686, green: 0.7098388672, blue: 0.3842281699, alpha: 1)))
                            .foregroundColor(Color (#colorLiteral(red: 0.3008493483, green: 0.6676690578, blue: 0.358412385, alpha: 1)))
                        .cornerRadius(15)}
                    
                    VStack(spacing: 20) {
                        Text("USER LOGIN")
                            .font(.title3)
                            .fontWeight(.semibold)
                            .bold()
                            .foregroundColor(.pink)
                        
                        HStack {
                            Image(systemName: "lock.fill")
                                .resizable(resizingMode: .stretch)
                                .foregroundColor(.blue)
                                .frame(width: 70, height: 40)
                            TextField("Password", text: $password)
                                .padding()
                                .padding()
                                .background(Color.white.opacity(0.15))
                                .cornerRadius(12)
                            .foregroundColor(.black)}
                        
                        Button("Forgot Password???🤣🤣"){}
                        
                            .padding(.bottom, 50)
                        
                        HStack {
                            Image(systemName: "person.fill")
                                .foregroundColor(.blue)
                            
                            
                            
                            TextField("Username", text: $username)
                                .padding()
                                .background(Color.white.opacity(0.15))
                                .cornerRadius(12)
                            .foregroundColor(.black)}
                        
                        
                        
                        
                        
                        if showError {
                            Text("Please enter username and password")
                                .font(.caption)
                                .foregroundColor(.red)
                        }
                        
                    } .frame(width: 370, height:170,)
                        .padding()
                        .background(
                            RoundedRectangle(cornerRadius: 25)
                                .fill(Color.white.opacity(0.6))
                                .shadow(color: .purple, radius: 20)
                        )
                        .padding(.top, 40)
                        .padding(.horizontal)
                    
                    Spacer()
                    
                    Button("HELP(touch me pls)🥺❤️🥺"){playSound(sound: "fahhhhh", type: "mp3")}
                        .foregroundColor(Color (#colorLiteral(red: 0.7142686248, green: 0.219291091, blue: 0.2283594012, alpha: 1)))
                        .padding(.bottom, 50)
                    
                }
            }
            
            
            .navigationDestination(isPresented: $isLoggedIn) {
                ContentView2()
            }
        }}
    
    func loginUser() {
        if username.isEmpty  {
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
