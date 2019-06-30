import UIKit
import Shared

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    private let window: UIWindow = UIWindow()

    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        let storyboard = UIStoryboard(name: "Main", bundle: Bundle.main)
        let vc = storyboard.instantiateInitialViewController()
        window.rootViewController = vc
        window.makeKeyAndVisible()
        return true
    }
}
