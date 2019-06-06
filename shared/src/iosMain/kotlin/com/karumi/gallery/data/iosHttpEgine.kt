package com.karumi.gallery.data

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.ios.Ios

actual fun getEngine(): HttpClientEngine = Ios.create()