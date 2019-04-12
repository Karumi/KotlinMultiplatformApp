package com.karumi.dribbble.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.pedrogomez.renderers.RVRendererAdapter
import com.pedrogomez.renderers.RendererBuilder

class MainActivity : AppCompatActivity() {

  private lateinit var adapter: RVRendererAdapter<ShotItem>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    title = "Dribbble"
    setContentView(R.layout.activity_main)
    configureRecyclerView()
  }

  private fun configureRecyclerView() {
    val builder = RendererBuilder<ShotItem>().bind(ShotItem::class.java, DribbbleRenderer())
    adapter = RVRendererAdapter<ShotItem>(builder)
    val recyclerView = findViewById<RecyclerView>(R.id.dribbbleList)

    recyclerView.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
    recyclerView.adapter = adapter

    adapter.addAll((0..100).map { (ShotItem("", "", "Saguaro National Park", "Nick Slater")) })
    adapter.notifyDataSetChanged()
  }
}
