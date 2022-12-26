import SwiftUI
import shared

struct ContentView: View {
    
    private let appModule = AppModule()
    
	var body: some View {
        TranslateScreen(
            historyDataSource: appModule.historyDataSource,
            translateUseCase: appModule.translateUseCase
        )
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
