package com.karumi.dribbble.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.karumi.dribbble.app.usecase.GetAllDribbbleShots
import com.karumi.dribbble.app.usecase.DribbbleShot
import com.pedrogomez.renderers.RVRendererAdapter
import com.pedrogomez.renderers.RendererBuilder

class MainActivity : AppCompatActivity(), DribbbleShotListPresenter.View {
  private lateinit var adapter: RVRendererAdapter<ShotItem>
  private lateinit var presenter: DribbbleShotListPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    configureRecyclerView()
    presenter = DribbbleShotListPresenter(this, GetAllDribbbleShots())
    presenter.onCreate()
  }

  private fun configureRecyclerView() {
    val builder = RendererBuilder<ShotItem>().bind(ShotItem::class.java, DribbbleRenderer())
    adapter = RVRendererAdapter<ShotItem>(builder)
    val recyclerView = findViewById<RecyclerView>(R.id.dribbbleList)

    recyclerView.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
    recyclerView.adapter = adapter
  }

  override fun plusAssign(shots: List<DribbbleShot>) {
    val items = shots.map { ShotItem(it.id, it.thumbnailUrl, it.name, it.authorName) }
    adapter.addAll(items)
    adapter.notifyDataSetChanged()
  }
}