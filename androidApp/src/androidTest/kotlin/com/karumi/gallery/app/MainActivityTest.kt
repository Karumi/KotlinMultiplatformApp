package com.karumi.gallery.app

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.karumi.gallery.data.local.LocalPhotosDataSource
import com.karumi.gallery.test.PhotoListStub
import com.karumi.gallery.data.network.PhotosApiClient
import com.karumi.gallery.model.PhotoShot
import com.karumi.photo.app.R
import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertListItemCount
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

  @get:Rule
  val activityRule = ActivityTestRule(MainActivity::class.java, true, false)

  @Test
  fun should_display_ten_photo_items_with_the_author_name() = runBlocking {
    givenPhotos()

    startActivity()

    assertListItemCount(R.id.photoList, 10)
  }

  @Test
  fun should_not_display_error_text_when_there_are_items() = runBlocking {
    givenPhotos()

    startActivity()

    assertNotDisplayed(R.string.oops_something_went_wrong)
  }

  @Test
  fun should_display_error_text_when_there_is_an_error() {
    givenApiPhotosError()

    startActivity()

    assertDisplayed(R.string.oops_something_went_wrong)
  }

  @Test
  fun should_display_progress_before_getting_photos() {
    startActivity()

    assertDisplayed(R.id.progress)
  }

  @Test
  fun should_display_app_title_on_toolbar() = runBlocking {
    givenPhotos()

    startActivity()

    assertDisplayed(R.string.app_name)
  }

  @Test
  fun should_not_display_progress_after_getting_photos() = runBlocking {
    givenPhotos()

    startActivity()

    assertNotDisplayed(R.id.progress)
  }

  @Test
  fun should_display_ten_photo_items_from_local_storage() = runBlocking {
    givenPhotos(forceExpire = false)

    startActivity()

    assertListItemCount(R.id.photoList, 10)
  }

  private fun givenApiPhotosError() {
    val apiClient = mock<PhotosApiClient> {
      onBlocking { getPhotos() } doThrow NullPointerException()
    }
    val localDataSource = mock<LocalPhotosDataSource>()
    given(localDataSource.isExpired()).willReturn(true)

    GalleryInjector.config(PhotoListStub(apiClient, localDataSource))
  }

  private fun givenPhotos(number: Int = 10, forceExpire: Boolean = true) =
    (1..number).map { index ->
      PhotoShot(
        id = index.toString(),
        authorName = "Author $index",
        thumbnailUrl = "https://images.unsplash.com/photo-1505816014357-96b5ff457e9a?ixlib" +
          "=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=400&q=80",
        numberOfLikes = 0
      )
    }.also { photos ->
      val apiClient = mock<PhotosApiClient> {
        onBlocking { getPhotos() } doReturn photos
      }
      val localDataSource = mock<LocalPhotosDataSource>()
      given(localDataSource.isExpired()).willReturn(forceExpire)
      given(localDataSource.getPhotos()).willReturn(photos)
      doNothing().whenever(localDataSource).insert(photos)
      doNothing().whenever(localDataSource).removeAll()

      GalleryInjector.config(PhotoListStub(apiClient, localDataSource))
    }

  private fun startActivity() {
    activityRule.launchActivity(null)
  }
}