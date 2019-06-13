import UIKit
import Shared

class ListViewController: UIViewController, PhotoListPresenterView {
    
    private var items: Array<PhotoListItem> = Array()
    private var presenter: PhotoListPresenter!
    @IBOutlet weak var titleView: UILabel!
    @IBOutlet weak var allItemsCollectionView: UICollectionView!
    @IBOutlet weak var loadingIndicator: UIActivityIndicatorView!
    @IBOutlet weak var errorText: UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        allItemsCollectionView.dataSource = self
        allItemsCollectionView.delegate = self
        allItemsCollectionView.register(UINib(nibName: "PhotoCollectionViewCell", bundle: Bundle.main), forCellWithReuseIdentifier: "PhotoCollectionViewCellReuseIdentifier")
        presenter = PhotoListPresenter(view: self, getAllPhotos: GalleryInjector().use.getPhotos)
        presenter.onCreate()
        errorText.isHidden = true
    }

    func plusAssign(shots: [PhotoShot]) {
        let newItems = shots.map(PhotoListItem.init)
        items.append(contentsOf: newItems)
        allItemsCollectionView.reloadData()
    }
    
    func showLoader() {
        loadingIndicator.startAnimating()
    }
    
    func hideLoader() {
        loadingIndicator.stopAnimating()
        loadingIndicator.isHidden = true
    }
    
    func onLoadError() {
        errorText.isHidden = false
        errorText.text = "Oops something went wrong!"
        NSLog("LOL ERROR")
    }
}

extension ListViewController: UICollectionViewDelegateFlowLayout {
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        return CGSize(width: collectionView.bounds.width / 2 - 15, height: 180)
    }
}

extension ListViewController: UICollectionViewDataSource {
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return items.count
    }

    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "PhotoCollectionViewCellReuseIdentifier", for: indexPath)

        if let cell = cell as? PhotoCollectionViewCell {
            cell.configure(withItem: self.items[indexPath.item])
        }

        return cell
    }
}

struct PhotoListItem {
    let id: String
    let thumbnailUrl: String
    let authorName: String

    init(item: PhotoShot) {
        self.id = item.id
        self.thumbnailUrl = item.thumbnailUrl
        self.authorName = item.authorName
    }
}
