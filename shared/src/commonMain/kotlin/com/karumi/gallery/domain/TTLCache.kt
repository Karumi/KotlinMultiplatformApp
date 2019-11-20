package com.karumi.gallery.domain

import kotlin.time.Duration

class TTLCache(
  private val timeStorage: TimeStorage,
  private val timeProvider: TimeProvider,
  ttl: Duration
) {

  private val ttlInMillis = ttl.toLongMilliseconds()

  fun isExpired(): Boolean =
    timeStorage.getPersistedTime() + ttlInMillis <= timeProvider.getCurrentTime()

  fun persistTime() = timeStorage.persistTime(timeProvider.getCurrentTime())
}