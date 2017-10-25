package com.framgia.fbook.screen.main

/**
 * Created by Hyperion on 25/10/2017.
 * Contact me thuanpx1710@gmail.com.
 * Thank you !
 */
interface LoginListener {
  interface LoginOnMyBook {
    fun onLoggedIn()
  }

  interface LoginOnNotification {
    fun onLoggedIn()
  }

  interface LoginOnMenuProfile {
    fun onLoggedIn()
  }
}
