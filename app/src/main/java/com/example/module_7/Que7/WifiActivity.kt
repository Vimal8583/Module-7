package com.example.module_7.Que7

import android.content.Context
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import android.widget.Toast
import com.example.module_7.R
import com.example.module_7.databinding.ActivityWifiBinding

class WifiActivity : AppCompatActivity() {
    private lateinit var binding : ActivityWifiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWifiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.switchWifi.setOnCheckedChangeListener { _, isChecked ->
            toggleWifi(isChecked)
        }
    }

    private fun toggleWifi(isEnabled: Boolean) {

            var wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        if (isEnabled) {

            wifiManager.isWifiEnabled = true
            Toast.makeText(this, "Wifi is On", Toast.LENGTH_SHORT).show()
        } else {
            wifiManager.isWifiEnabled = false
        }
    }
}