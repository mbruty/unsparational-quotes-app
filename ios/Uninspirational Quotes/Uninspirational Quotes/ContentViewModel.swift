//
//  ContentViewModel.swift
//  Uninspirational Quotes
//
//  Created by Mike Bruty on 10/09/2021.
//

import Foundation
import SwiftUI

struct Quote: Codable, Identifiable {
    let id, quote, createdAt, updatedAt, name: String
    let v: Int

    enum CodingKeys: String, CodingKey {
        case id = "_id"
        case quote, createdAt, updatedAt, name
        case v = "__v"
    }
}

enum RequestState {
    case loading
    case done
}

final class ContentViewModel: ObservableObject {
    @Published var quotes: [Quote]
    @Published var state: RequestState = .loading
    private var index: Int = 0
    init() {
        // Fetch a quote
        quotes = [Quote]()
        fetchQuotes()
    }
    
    public func fetchQuotes() {
        URLSession.shared.dataTask(with: URL(string: "https://uninspired-quotes.herokuapp.com/api/quote")!) { (data, _, error) in
            if(error != nil) {
                print(error?.localizedDescription ?? "Unkown error")
                return
            }
            let newQuotes = try! JSONDecoder().decode([Quote].self, from: data!)
            DispatchQueue.main.async {
                for quote in newQuotes {
                    self.quotes.append(quote)
                }
                self.state = .done
            }
        }.resume()
    }
}
