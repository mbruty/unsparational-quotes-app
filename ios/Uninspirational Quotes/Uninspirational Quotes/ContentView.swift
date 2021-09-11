//
//  ContentView.swift
//  Uninspirational Quotes
//
//  Created by Mike Bruty on 10/09/2021.
//

import SwiftUI

struct ContentView: View {
    @ObservedObject private var viewModel = ContentViewModel()
    var body: some View {
        if(viewModel.state == .loading) {
            Text("Feting you a quote...")
                .font(.largeTitle)
                .padding()
        } else {
            TabView {
                ForEach(viewModel.quotes) { quote in
                    VStack {
                        Text(quote.quote)
                            .font(.largeTitle)
                            .padding()
                        HStack {
                            Text("Quote from")
                                .font(.caption)
                            Text(quote.name)
                                .font(.caption)
                            Text(quote.createdAt)
                                .font(.caption)
                        }
                    }.frame(width: UIScreen.main.bounds.width)
                    
                }
            }
            .tabViewStyle(PageTabViewStyle(indexDisplayMode: .always))
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
