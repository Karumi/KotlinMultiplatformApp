import Foundation
import UIKit
import KIF
import Nimble
import Shared
@testable import Dribbble_app

class PhotoListViewControllerTests: AcceptanceTestCase {
    
    //should_display_ten_photo_items_with_the_author_name
    //should_not_display_error_text_when_there_are_items
    //should_display_error_text_when_there_is_an_error
    //should_display_progress_before_getting_photos
    //should_display_app_title_on_toolbar
    //should_not_display_progress_after_getting_photos
    
    func testDisplayTenPhotoItemsWithTheAuthorName() {
        givenPhotos()
        
        openPhotoListViewController
        
        let tableView = tester().waitForView(withAccessibilityLabel: "PhotoCollectionViewCellReuseIdentifier") as! UICollectionView
        expect(tableView.numberOfItems(inSection: 0)).to(equal(10))
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
        let injectionModule = InjectionModule()
        injectionModule.getPhotosApiClient = PhotoApiClientStub(photos: photos)
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
}
