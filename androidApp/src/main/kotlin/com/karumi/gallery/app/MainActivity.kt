package com.karumi.gallery.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.karumi.gallery.data.getPhotosApiClient
import com.karumi.gallery.model.PhotoShot
import com.karumi.gallery.usecase.GetPhotos
import com.karumi.photo.app.R
import com.pedrogomez.renderers.RVRendererAdapter
import com.pedrogomez.renderers.RendererBuilder

class MainActivity : AppCompatActivity(), PhotoListPresenter.View {
  private lateinit var adapter: RVRendererAdapter<PhotoItem>
  private lateinit var presenter: PhotoListPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    configureRecyclerView()
    presenter = PhotoListPresenter(
      this,
      GetPhotos(getPhotosApiClient())
    )
    presenter.onCreate()
  }

  private fun configureRecyclerView() {
    val builder = RendererBuilder<PhotoItem>().bind(PhotoItem::class.java, PhotosRenderer())
    adapter = RVRendererAdapter<PhotoItem>(builder)
    val recyclerView = findViewById<RecyclerView>(R.id.photoList)

    recyclerView.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
    recyclerView.adapter = adapter
  }

  override fun plusAssign(shots: List<PhotoShot>) {
    val items = shots.map { PhotoItem(it.id, it.thumbnailUrl, it.authorName) }
    adapter.addAll(items)
    adapter.notifyDataSetChanged()
  }
}