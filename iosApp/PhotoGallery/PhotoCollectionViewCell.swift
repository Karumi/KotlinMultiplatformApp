import UIKit
import SDWebImage

class PhotoCollectionViewCell: UICollectionViewCell {
    @IBOutlet weak var imageBackView: UIView!
    @IBOutlet weak var imageView: UIImageView!
    @IBOutlet weak var authorName: UILabel!

    func configure(withItem item: PhotoListItem) {
        imageBackView.layer.cornerRadius = 4
        imageBackView.layer.shadowColor = UIColor.black.cgColor
        imageBackView.layer.shadowOffset = CGSize.zero
        imageBackView.layer.shadowRadius = 2
        imageBackView.layer.shadowOpacity = 1
        
        authorName.text = "by \(item.authorName)"
        imageView.sd_setImage(with: URL(string: item.thumbnailUrl), completed: nil)
    }
}
