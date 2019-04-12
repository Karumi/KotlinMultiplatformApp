package com.karumi.dribbble.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.pedrogomez.renderers.RVRendererAdapter
import com.pedrogomez.renderers.RendererBuilder

class MainActivity : AppCompatActivity() {

  private lateinit var adapter: RVRendererAdapter<DribbbleItem>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    title = "Dribbble"
    setContentView(R.layout.activity_main)
    configureRecyclerView()
  }

  private fun configureRecyclerView() {
    val builder = RendererBuilder<DribbbleItem>().bind(DribbbleItem::class.java, DribbbleRenderer())
    adapter = RVRendererAdapter<DribbbleItem>(builder)
    val recyclerView = findViewById<RecyclerView>(R.id.dribbbleList)

    recyclerView.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
    recyclerView.adapter = adapter

    adapter.addAll((0..100).map { (DribbbleItem("", "", "Saguaro National Park", "Nick Slater")) })
    adapter.notifyDataSetChanged()
  }
}
