package com.karumi.dribbble.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.pedrogomez.renderers.Renderer

data class DribbbleItem(val id: String, val thumbnailUrl: String, val name: String, val authorName: String)

class DribbbleRenderer : Renderer<DribbbleItem>() {

  private lateinit var dribbbleImageView: ImageView
  private lateinit var dribbbleNameView: TextView
  private lateinit var dribbbleAuthorNameView: TextView

  override fun inflate(inflater: LayoutInflater?, parent: ViewGroup?): View =
          inflater?.inflate(R.layout.item_dribbble, parent, false)!!

  override fun hookListeners(rootView: View?) {}

  override fun render() {
    dribbbleNameView.text = content.name
    dribbbleAuthorNameView.text = context.getString(R.string.dribbbles_list_author, content.authorName)
  }

  override fun setUpView(rootView: View?) {
    rootView ?: return
    dribbbleImageView = rootView.findViewById(R.id.dribbbleImage)
    dribbbleNameView = rootView.findViewById(R.id.dribbbleName)
    dribbbleAuthorNameView = rootView.findViewById(R.id.dribbbleAuthorName)
  }
}