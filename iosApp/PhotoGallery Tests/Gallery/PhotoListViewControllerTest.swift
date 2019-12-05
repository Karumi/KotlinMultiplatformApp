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
        
        expectToShowPhotoList(withNumberOfItems: 10)
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
        givenAlwaysLoading()

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

    private func givenAlwaysLoading() {
        let injectionModule = TestModule(
            apiClient: PhotoApiClientStub(stub: PhotosStub.Loading()),
            localDataSource: PhotoDataSourceStub(stub: PhotosStub.Loading(), isExpired: true)
        )
        GalleryInjector().config(injector: injectionModule)
    }
    
    private func givenAnError() {
        let injectionModule = TestModule(
            apiClient: PhotoApiClientStub(stub: PhotosStub.Error()),
            localDataSource: PhotoDataSourceStub(stub: PhotosStub.Loading(), isExpired: true)
        )
        GalleryInjector().config(injector: injectionModule)
    }
    
    private func givenPhotos(_ photoNumber: Int = 10) {
        var photos = [PhotoShot]()
        for index in 0..<photoNumber {
            let photo = PhotoShot(
                id: "\(index)",
                thumbnailUrl: "https://images.unsplash.com/photo-1505816014357-96b5ff457e9a?ixlib" +
                "=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=400&q=80",
                authorName: "Author \(index)",
                numberOfLikes: 0
            )
            photos.append(photo)
        }
        let photoApiClient = PhotoApiClientStub(stub: PhotosStub.Success(photos: photos))
        let localDataSource = PhotoDataSourceStub(stub: PhotosStub.Loading(), isExpired: true)
        let injectionModule = TestModule(apiClient: photoApiClient, localDataSource: localDataSource)
        GalleryInjector().config(injector: injectionModule)
    }
    
    @discardableResult
    private func openPhotoListViewController() -> PhotoListViewController {
        let storyboard = UIStoryboard(name: "Main", bundle: Bundle.main)
        let photosViewController = storyboard.instantiateViewController(withIdentifier: "PhotoListViewController") as! PhotoListViewController
        let rootViewController = UINavigationController()
        rootViewController.viewControllers = [photosViewController]
        present(viewController: rootViewController)
        tester().waitForAnimationsToFinish()
        return photosViewController
    }

    private func expectToShowPhotoList(withNumberOfItems numberOfItems: Int) {
        let tableView = tester().waitForView(withAccessibilityLabel: "PhotoCollectionView") as! UICollectionView
        tester().waitForCell(at: IndexPath(item: 0, section: 0), inCollectionViewWithAccessibilityIdentifier: "PhotoCollectionView")
        expect(tableView.numberOfItems(inSection: 0)).to(equal(numberOfItems))
    }
    
    class TestModule: InjectionModule {
        
        private var apiClient: PhotosApiClient
        private var localDataSource: LocalPhotosDataSource
        
        init(apiClient: PhotosApiClient, localDataSource: LocalPhotosDataSource) {
            self.apiClient = apiClient
            self.localDataSource = localDataSource
        }
        
        override func getPhotosApiClient() -> PhotosApiClient {
            return self.apiClient
        }
        
        override func getLocalPhotosDataSource() -> LocalPhotosDataSource {
            return self.localDataSource
        }
    }
}
