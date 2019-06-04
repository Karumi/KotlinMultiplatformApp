package com.karumi.gallery

expect fun logError(tag: String, message: String)
expect fun logInfo(tag: String, message: String)

expect class Sample() {
  fun checkMe(): Int
}

expect object Platform {
  val name: String
}

fun hello(): String = "Hello from ${Platform.name}"

class Proxy {
  fun proxyHello() = hello()
}

fun main() {
  println(hello())
}