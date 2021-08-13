package com.ashish.remotecontrolapp

import android.content.Intent
import android.provider.Settings
import android.text.TextUtils.SimpleStringSplitter
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ashish.remotecontrolapp.services.MyAccessibilityService


class MainActivity : AppCompatActivity() {
    override fun onResume() {
        super.onResume()
        if (!isMyAccessibilityServiceEnabled()) {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            startActivity(intent)
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            startActivity(intent)
        } else {
            finish()
        }
    }

    private fun isMyAccessibilityServiceEnabled(): Boolean {
        var accessibilityEnabled = 0
        val service = packageName + "/" + MyAccessibilityService::class.java.canonicalName
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                contentResolver,
                Settings.Secure.ACCESSIBILITY_ENABLED
            )
        } catch (e: Settings.SettingNotFoundException) {
            Log.e(
                MainActivity::class.java.simpleName,
                "Error finding setting, default accessibility to not found: ",
                e
            )
        }
        val mStringColonSplitter = SimpleStringSplitter(':')
        if (accessibilityEnabled == 1) {
            val settingValue: String = Settings.Secure.getString(
                contentResolver,
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
            )
            mStringColonSplitter.setString(settingValue)
            while (mStringColonSplitter.hasNext()) {
                val accessibilityService = mStringColonSplitter.next()
                if (accessibilityService.equals(service, ignoreCase = true)) {
                    return true
                }
            }
        } else {
            Log.v(MainActivity::class.java.simpleName, "***ACCESSIBILITY IS DISABLED***")
        }
        return false
    }
}