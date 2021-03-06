package com.karumi.gallery.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.karumi.photo.app.R
import com.pedrogomez.renderers.Renderer
import com.squareup.picasso.Picasso

data class PhotoItem(
  val id: String,
  val thumbnailUrl: String,
  val authorName: String
)

class PhotosRenderer : Renderer<PhotoItem>() {

  private lateinit var shotImageView: ImageView
  private lateinit var shotAuthorNameView: TextView

  override fun inflate(inflater: LayoutInflater?, parent: ViewGroup?): View =
    inflater?.inflate(R.layout.item_photo, parent, false)!!

  override fun hookListeners(rootView: View?) {}

  override fun render() {
    shotAuthorNameView.text = context.getString(
      R.string.photos_list_shot_author,
      content.authorName
    )
    Picasso.get()
      .load(content.thumbnailUrl)
      .fit()
      .centerCrop()
      .into(shotImageView)
  }

  override fun setUpView(rootView: View?) {
    rootView ?: return
    shotImageView = rootView.findViewById(R.id.shotImage)
    shotAuthorNameView = rootView.findViewById(R.id.shotAuthorName)
  }
}