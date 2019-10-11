package com.karumi.gallery.data

import com.karumi.gallery.GalleryDb
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.ios.NativeSqliteDriver

@Suppress("unused")
fun defaultDriver(): SqlDriver = NativeSqliteDriver(GalleryDb.Schema, "gallerydb")