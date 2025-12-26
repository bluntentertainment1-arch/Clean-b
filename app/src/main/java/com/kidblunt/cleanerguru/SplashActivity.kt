package com.kidblunt.cleanerguru

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.kidblunt.cleanerguru.ui.components.AnimatedSplashScreen
import com.kidblunt.cleanerguru.ui.theme.CleanerGuruTheme
import kotlinx.coroutines.delay

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        
        setContent {
            CleanerGuruTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AnimatedSplashScreen {
                        // Navigate to MainActivity after splash animation
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }
}