package com.karumi.gallery.domain

import kotlin.time.Duration

class TTLCache(
  private val timeProvider: TimeProvider,
  private val ttl: Duration
) {

  private val ttlInMillis = ttl.toLongMilliseconds()

  fun isExpired(): Boolean =
    timeProvider.getPersistedTime() + ttlInMillis <= timeProvider.getCurrentTime()

  fun persisTime() = timeProvider.persistTime()
}