import UIKit
import main

class ListViewController: UIViewController, DribbbleShotListPresenterView {

    private var items: Array<PhotoListItem> = Array()
    private var presenter: DribbbleShotListPresenter!
    @IBOutlet weak var titleView: UILabel!
    @IBOutlet weak var allItemsCollectionView: UICollectionView!

    override func viewDidLoad() {
        super.viewDidLoad()
        allItemsCollectionView.dataSource = self
        allItemsCollectionView.delegate = self
        allItemsCollectionView.register(UINib(nibName: "PhotoCollectionViewCell", bundle: Bundle.main), forCellWithReuseIdentifier: "PhotoCollectionViewCellReuseIdentifier")
        presenter = DribbbleShotListPresenter(view: self, getAllDribbbleShots: GetAllDribbbleShots())
        presenter.onCreate()
    }

    func plusAssign(shots: [DribbbleShot]) {
        let newItems = shots.map { PhotoListItem(id: $0.id, thumbnailUrl: $0.thumbnailUrl, name: $0.name, authorName: $0.authorName) }
        items.append(contentsOf: newItems)
        allItemsCollectionView.reloadData()
    }
}

extension ListViewController: UICollectionViewDelegateFlowLayout {
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        return CGSize(width: collectionView.bounds.width / 2 - 32, height: 180)
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
    let name: String
    let authorName: String
}
