import UIKit

class PhotoCollectionViewCell: UICollectionViewCell {
    @IBOutlet weak var imageView: UIImageView!
    @IBOutlet weak var name: UILabel!
    @IBOutlet weak var authorName: UILabel!

    func configure(withItem item: PhotoListItem) {
        name.text = item.name
        authorName.text = item.authorName
    }
}
