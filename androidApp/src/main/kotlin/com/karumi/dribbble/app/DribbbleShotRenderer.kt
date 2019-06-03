package com.karumi.dribbble.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.pedrogomez.renderers.Renderer

data class ShotItem(
  val id: String,
  val thumbnailUrl: String,
  val name: String,
  val authorName: String
)

class DribbbleRenderer : Renderer<ShotItem>() {

  private lateinit var shotImageView: ImageView
  private lateinit var shotNameView: TextView
  private lateinit var shotAuthorNameView: TextView

  override fun inflate(inflater: LayoutInflater?, parent: ViewGroup?): View =
    inflater?.inflate(R.layout.item_dribbble, parent, false)!!

  override fun hookListeners(rootView: View?) {}

  override fun render() {
    shotNameView.text = content.name
    shotAuthorNameView.text = context.getString(
      R.string.dribbbles_list_shot_author,
      content.authorName
    )
  }

  override fun setUpView(rootView: View?) {
    rootView ?: return
    shotImageView = rootView.findViewById(R.id.shotImage)
    shotNameView = rootView.findViewById(R.id.shotName)
    shotAuthorNameView = rootView.findViewById(R.id.shotAuthorName)
  }
}