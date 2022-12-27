//
//  MicrophonePowerObserver.swift
//  iosApp
//
//  Created by Amal Nuritdinkhodzhaev on 27/12/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared
import Speech
import Combine

class MicropthonePowerObserver: ObservableObject {
    private var cancelable: AnyCancellable? = nil
    private var audioRecorder: AVAudioRecorder? = nil
    
    @Published private(set) var micPowerRatio = 0.0
    private let powerRatioEmissionsPerSecond = 20.0
    
    func startObserving() {
        do {
            let recorderSettings: [String: Any] = [
                AVFormatIDKey: NSNumber(value: kAudioFormatAppleLossless),
                AVNumberOfChannelsKey: 1
            ]
            let recorder = try AVAudioRecorder(
                url: URL(fileURLWithPath: "/dev/null", isDirectory: true),
                settings: recorderSettings
            )
            recorder.isMeteringEnabled = true
            recorder.record()
            self.audioRecorder = recorder
            
            self.cancelable = Timer.publish(
                every: 1.0 / powerRatioEmissionsPerSecond,
                tolerance: 1.0 / powerRatioEmissionsPerSecond,
                on: .main,
                in: .common
            )
            .autoconnect()
            .sink { [weak self] _ in
                recorder.updateMeters()
                
                let powerOffset = recorder.averagePower(forChannel: 0)
                if powerOffset < -50 {
                    self?.micPowerRatio = 0.0
                } else {
                    let normalizedOffset = CGFloat(50 + powerOffset) / 50
                    self?.micPowerRatio = normalizedOffset
                }
            }
        } catch {
            print("An error occurred when observing microphone power: \(error.localizedDescription)")
        }
    }
    
    func release() {
        cancelable = nil
        
        audioRecorder?.stop()
        audioRecorder = nil
        
        micPowerRatio = 0.0
    }
}
