package com.framgia.fbook.screen.notification

import com.framgia.fbook.data.model.ItemNotification

/**
 * Created by Hyperion on 13/10/2017.
 * Contact me thuanpx1710@gmail.com.
 * Thank you !
 */
interface ItemNotificationClickListener {
  fun onItemNotificationClick(itemNotification: ItemNotification?, position: Int)
}
