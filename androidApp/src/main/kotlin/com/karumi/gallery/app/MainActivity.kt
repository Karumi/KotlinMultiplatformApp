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

  override fun render(state: PhotoListPresenter.View.State) {
    when (state) {
      is PhotoListPresenter.View.State.Loading -> renderLoading()
      is PhotoListPresenter.View.State.Error -> renderError()
      is PhotoListPresenter.View.State.Success -> renderPhotos(state.photos)
    }
  }

  private fun renderLoading() {
    progress.visibility = View.VISIBLE
    errorInformation.visibility = View.GONE
  }

  private fun renderError() {
    progress.visibility = View.GONE
    errorInformation.visibility = View.VISIBLE
  }

  private fun renderPhotos(photos: List<PhotoShot>) {
    progress.visibility = View.GONE
    errorInformation.visibility = View.GONE

    val items = photos.map { PhotoItem(it.id, it.thumbnailUrl, it.authorName) }
    adapter.clear()
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