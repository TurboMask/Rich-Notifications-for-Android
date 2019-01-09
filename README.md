# Rich Notifications for Android
This is an Android app to show rich notifications at specified time intervals. Default configuration is to show notifications once a day at 9:15. By default, notification content is a random image loaded from internet.

App's primary use case was to show notifications with images on Samsung Gear Fit 2 smartwatch, therefore image size is set to 204x204 to be displayed with 1-to-1 ratio on this smartwatch.

This app can be configured (by editing code) to show various notifications at specified time intervals on smartphone and smartwatch (if smartphone is connected to phone and supports rich notifications). Notification content is not limited to images - it can be rich text, text input, buttons etc. See [Android documentation](https://developer.android.com/training/notify-user/expanded) for more info.

App has 4 control buttons:

- Start: starts AlarmManager and registers notifications. Notifications will be shown at specified interval, indefinitely.
- Stop: stops showing notifications.
- Check: was meant to show time of nearest notification (alarm). Apparently it's only possible to get time of nearest system alarms, but not app-created ones.
- Show: shows one notification with 3 seconds delay. Delay is to give user some time to lock device, so notification is also transfered to smartwach, if one is connected.

![Alt text](images/01.jpg?raw=true "Rich notification on Samsung Gear Fit 2")
