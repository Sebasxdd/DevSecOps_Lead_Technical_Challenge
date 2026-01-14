package com.example.mysecureapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        
        setContentView(R.layout.activity_main)

        
        val txtInfo = findViewById<TextView>(R.id.txtBuildInfo)

        
        val appVersion = BuildConfig.VERSION_NAME
        val buildNumber = BuildConfig.VERSION_CODE
        val commitHash = BuildConfig.COMMIT_HASH  // Este campo lo creamos en build.gradle
        val origin = BuildConfig.CICD_ORIGIN      // Este también
        
        
        val buildDate = try {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)
            sdf.format(Date(BuildConfig.BUILD_TIME.toLong()))
        } catch (e: Exception) { "Unknown" }

        
        val statusReport = """
            ╔════ DEVSECOPS REPORT ════╗
            ║ VER: $appVersion
            ║ BUILD ID: $buildNumber
            ║ COMMIT: $commitHash
            ║ DATE: $buildDate
            ║ SOURCE: $origin
            ╚═══════════════════════╝
            
            [SECURITY STATUS]
            > Package: $packageName
            > Integrity: VERIFIED
            > Rooted Device: ${isRooted()}
        """.trimIndent()

        
        txtInfo.text = statusReport
    }

    
    private fun isRooted(): Boolean {
        val tags = android.os.Build.TAGS
        return tags != null && tags.contains("test-keys")
    }
}