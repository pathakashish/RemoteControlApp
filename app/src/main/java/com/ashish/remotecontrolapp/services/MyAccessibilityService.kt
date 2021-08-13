package com.ashish.remotecontrolapp.services

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityManager

class MyAccessibilityService : AccessibilityService(),
    AccessibilityManager.TouchExplorationStateChangeListener {

    private lateinit var accessibilityManager: AccessibilityManager

    /**
     * This system calls this method when it successfully connects to your accessibility service.
     * Use this method to do any one-time setup steps for your service, including connecting to user
     * feedback system services, such as the audio manager or device vibrator. If you want to set
     * the configuration of your service at runtime or make one-time adjustments, this is a
     * convenient location from which to call setServiceInfo().
     */
    override fun onServiceConnected() {
        super.onServiceConnected()
        initAccessibilityManager()
        initTouchExplorationCapability()
    }

    private fun initTouchExplorationCapability() {
        accessibilityManager.addTouchExplorationStateChangeListener(this)
        if (accessibilityManager.isTouchExplorationEnabled) {
            requestTouchExploration()
        }
    }

    private fun initAccessibilityManager() {
        accessibilityManager = getSystemService(ACCESSIBILITY_SERVICE) as AccessibilityManager
    }

    /**
     * This method is called back by the system when it detects an AccessibilityEvent that matches
     * the event filtering parameters specified by your accessibility service. For example, when the
     * user clicks a button or focuses on a user interface control in an application for which your
     * accessibility service is providing feedback. When this happens, the system calls this method,
     * passing the associated AccessibilityEvent, which the service can then interpret and use to
     * provide feedback to the user. This method may be called many times over the lifecycle of your
     * service.
     */
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {

    }

    /**
     * This method is called when the system wants to interrupt the feedback your service is
     * providing, usually in response to a user action such as moving focus to a different control.
     * This method may be called many times over the lifecycle of your service.
     */
    override fun onInterrupt() {

    }

    /**
     * Called by the system when the service is being shutdown for any reason
     */
    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    override fun onTouchExplorationStateChanged(enabled: Boolean) {
        if (!enabled) {
            requestTouchExploration()
        }
    }

    private fun requestTouchExploration() {
        // Start an Intent to enable this service and touch exploration
    }
}