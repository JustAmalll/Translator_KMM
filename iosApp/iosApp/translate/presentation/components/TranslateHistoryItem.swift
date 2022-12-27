//
//  TranslateHistoryItem.swift
//  iosApp
//
//  Created by Amal Nuritdinkhodzhaev on 27/12/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct TranslateHistoryItem: View {
    
    let item: UiHistoryItem
    let onClick: () -> Void
    
    var body: some View {
        Button(action: onClick) {
            VStack(alignment: .leading) {
                HStack {
                    SmallLanguageIcon(language: item.fromLanguage)
                    Text(item.fromText)
                        .foregroundColor(.lightBlue)
                        .font(.body)
                }
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding(.bottom)
                
                HStack {
                    SmallLanguageIcon(language: item.toLanguage)
                    Text(item.toText)
                        .foregroundColor(.onSurface)
                        .font(.body.weight(.semibold))
                }
                .frame(maxWidth: .infinity, alignment: .leading)
            }
            .frame(maxWidth: .infinity)
            .padding()
            .gradientSurface()
            .cornerRadius(15)
            .shadow(radius: 4)
        }
    }
}

struct TranslateHistoryItem_Previews: PreviewProvider {
    static var previews: some View {
        TranslateHistoryItem(
            item: UiHistoryItem(
                id: 0, fromText: "Привет",
                toText: "Hi",
                fromLanguage: UiLanguage(language: .russian, imageName: "russian"),
                toLanguage: UiLanguage(language: .english, imageName: "english")),
            onClick: {}
        )
    }
}
