package com.karumi.dribbble.app

import com.karumi.dribbble.app.usecase.GetAllDribbbleShots
import com.karumi.dribbble.app.usecase.DribbbleShot

class DribbbleShotListPresenter(
  private val view: View,
  private val getAllDribbbleShots: GetAllDribbbleShots
) {

  fun onCreate() {
    val allShots = getAllDribbbleShots()
    view += allShots
  }

  interface View {
    operator fun plusAssign(shots: List<DribbbleShot>)
  }
}