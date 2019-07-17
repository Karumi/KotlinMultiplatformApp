import Foundation
import UIKit
import KIF
import Nimble
@testable import PhotoGallery
import Shared

class PhotoListViewControllerTests: AcceptanceTestCase {
    
    func testDisplayTenPhotoItemsWithTheAuthorName() {
        givenPhotos()
        
        openPhotoListViewController()
        
        let tableView = tester().waitForView(withAccessibilityLabel: "PhotoCollectionView") as! UICollectionView
        expect(tableView.numberOfItems(inSection: 0)).to(equal(10))
    }
    
    func testNotDisplayErrorTextWhenThereAreItems() {
        givenPhotos()
        
        openPhotoListViewController()
        
        tester().waitForAbsenceOfView(withAccessibilityLabel: "Oops something went wrong!")
    }
    
    func testDisplayErrorTextWhenThereIsAnError() {
        givenAnError()
        
        openPhotoListViewController()
        
        tester().waitForView(withAccessibilityLabel: "Oops something went wrong!")
    }
    
    func testDisplayLoadingActivityBeforeGettingPhotos() {
        openPhotoListViewController()
        
        tester().waitForView(withAccessibilityLabel: "LoadingView")
    }
    
    func testNotDisplayLoadingActivityAfterGettingPhotos() {
        givenPhotos()
        
        openPhotoListViewController()
        
        tester().waitForAbsenceOfView(withAccessibilityLabel: "LoadingView")
    }
    
    func testDisplayAppTitle() {
        givenPhotos()
        
        openPhotoListViewController()
        
        tester().waitForView(withAccessibilityLabel: "Gallery")
    }
    
    private func givenAnError() {
        let injectionModule = TestModule(apiClient: PhotoApiClientStub(photos: [PhotoShot](), withErrors: true))
        GalleryInjector().config(injector: injectionModule)
    }
    
    private func givenPhotos(_ photoNumber: Int = 10) {
        var photos = [PhotoShot]()
        for index in 0..<photoNumber {
            let photo = PhotoShot(
                id: "\(index)",
                thumbnailUrl: "https://images.unsplash.com/photo-1505816014357-96b5ff457e9a?ixlib" +
                "=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=400&q=80",
                authorName: "Author \(index)"
            )
            photos.append(photo)
        }
        let injectionModule = TestModule(apiClient: PhotoApiClientStub(photos: photos, withErrors: false))
        GalleryInjector().config(injector: injectionModule)
    }
    
    @discardableResult
    fileprivate func openPhotoListViewController() -> PhotoListViewController {
        let storyboard = UIStoryboard(name: "Main", bundle: Bundle.main)
        let photosViewController = storyboard.instantiateViewController(withIdentifier: "PhotoListViewController") as! PhotoListViewController
        let rootViewController = UINavigationController()
        rootViewController.viewControllers = [photosViewController]
        present(viewController: rootViewController)
        tester().waitForAnimationsToFinish()
        return photosViewController
    }
    
    class TestModule: InjectionModule {
        
        private var apiClient: PhotosApiClient
        
        init(apiClient: PhotosApiClient) {
            self.apiClient = apiClient
        }
        
        override func getPhotosApiClient() -> PhotosApiClient {
            return self.apiClient
        }
    }
}
