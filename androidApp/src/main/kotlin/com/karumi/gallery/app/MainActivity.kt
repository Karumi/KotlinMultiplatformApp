package com.karumi.gallery.app

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.karumi.gallery.model.PhotoShot
import com.karumi.photo.app.R
import com.pedrogomez.renderers.RVRendererAdapter
import com.pedrogomez.renderers.RendererBuilder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), PhotoListPresenter.View {

  private lateinit var adapter: RVRendererAdapter<PhotoItem>
  private lateinit var presenter: PhotoListPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(toolbar)

    configureRecyclerView()
    presenter = PhotoListPresenter(
      this,
      GalleryInjector().getPhotos()
    )
    presenter.onCreate()
  }

  override fun onDestroy() {
    super.onDestroy()
    presenter.detachView()
  }

  override fun showLoader() {
    progress.visibility = View.VISIBLE
  }

  override fun hideLoader() {
    progress.visibility = View.GONE
  }

  override fun onLoadError() {
    errorInformation.visibility = View.VISIBLE
  }

  override fun plusAssign(shots: List<PhotoShot>) {
    val items = shots.map { PhotoItem(it.id, it.thumbnailUrl, it.authorName) }
    adapter.addAll(items)
    adapter.notifyDataSetChanged()
  }

  private fun configureRecyclerView() {
    val builder = RendererBuilder<PhotoItem>().bind(PhotoItem::class.java, PhotosRenderer())
    adapter = RVRendererAdapter<PhotoItem>(builder)

    photoList.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
    photoList.adapter = adapter
  }
}