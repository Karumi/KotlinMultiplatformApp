import Foundation
import KIF
import Nimble

class AcceptanceTestCase: KIFTestCase {
    
    fileprivate var originalRootViewController: UIViewController?
    fileprivate var rootViewController: UIViewController? {
        get {
            return UIApplication.shared.keyWindow?.rootViewController
        }
        
        set(newRootViewController) {
            UIApplication.shared.keyWindow?.rootViewController = newRootViewController
        }
    }
    
    override func tearDown() {
        super.tearDown()
        if let originalRootViewController = originalRootViewController {
            rootViewController = originalRootViewController
        }
    }
    
    func present(viewController: UIViewController) {
        originalRootViewController = rootViewController
        rootViewController = viewController
    }
}
