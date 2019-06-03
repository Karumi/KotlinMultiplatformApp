import UIKit

class PhotoCollectionViewCell: UICollectionViewCell {
    @IBOutlet weak var imageBackView: UIView!
    @IBOutlet weak var imageView: UIImageView!
    @IBOutlet weak var name: UILabel!
    @IBOutlet weak var authorName: UILabel!

    func configure(withItem item: PhotoListItem) {
        imageBackView.layer.cornerRadius = 4
        imageBackView.layer.shadowColor = UIColor.black.cgColor
        imageBackView.layer.shadowOffset = CGSize.zero
        imageBackView.layer.shadowRadius = 2
        imageBackView.layer.shadowOpacity = 1

        name.text = item.name
        authorName.text = item.authorName
    }
}
