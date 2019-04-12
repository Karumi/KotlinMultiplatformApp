package com.karumi.dribbble.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pedrogomez.renderers.Renderer

data class DribbbleItem(val id: String, val thumbnailUrl: String, val name: String, val authorName: String)

class DribbbleRenderer : Renderer<DribbbleItem>() {
  override fun inflate(inflater: LayoutInflater?, parent: ViewGroup?): View {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun hookListeners(rootView: View?) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun render() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun setUpView(rootView: View?) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}