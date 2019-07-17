package com.karumi.gallery.data

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.android.AndroidClientEngine
import io.ktor.client.engine.android.AndroidEngineConfig

actual fun getEngine(): HttpClientEngine = AndroidClientEngine(AndroidEngineConfig())