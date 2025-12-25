@file:OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
package com.kidblunt.cleanerguru.ui.screens
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import com.kidblunt.cleanerguru.R

import android.app.ActivityManager
import android.content.Context
import android.os.BatteryManager
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kidblunt.cleanerguru.ui.theme.*
import com.kidblunt.cleanerguru.manager.BatterySaverManager
import kotlinx.coroutines.delay

@Composable
fun BatterySaverScreen(
    onBack: () -> Unit
) {
    // Battery saver states (NO persistence)
    var powerSaverActive by remember { mutableStateOf(false) }
    var ultraSaverActive by remember { mutableStateOf(false) }

    // Inactivity tracking
    var lastInteractionTime by remember {
        mutableStateOf(System.currentTimeMillis())
    }

    var inactivityHandled by remember { mutableStateOf(false) }

    fun registerInteraction() {
        lastInteractionTime = System.currentTimeMillis()
        inactivityHandled = false
    }

    fun turnOffBatterySaving() {
        powerSaverActive = false
        ultraSaverActive = false
    }

    // Auto-off after 1 hour inactivity
    LaunchedEffect(Unit) {
        while (true) {
            delay(60_000)
            val inactiveMinutes =
                (System.currentTimeMillis() - lastInteractionTime) / (1000 * 60)

            if (inactiveMinutes >= 60 && !inactivityHandled) {
                turnOffBatterySaving()
                inactivityHandled = true
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        /* ================= Header ================= */

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onBack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = stringResource(id = R.string.battery_saver),
                style = MaterialTheme.typography.titleLarge
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        /* ================= Power Saver ================= */

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.battery_saver_regular),
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.titleMedium
            )

            Switch(
                checked = powerSaverActive,
                onCheckedChange = { enabled ->
                    registerInteraction()
                    powerSaverActive = enabled

                    if (enabled) {
                        // Power Saver ON → Ultra Saver OFF
                        ultraSaverActive = false
                    }
                }
            )
        }

        Text(
            text = stringResource(id = R.string.battery_saver_regular_desc),
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        /* ================= Ultra Saver ================= */

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.battery_saver_ultra),
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.titleMedium
            )

            Switch(
                checked = ultraSaverActive,
                onCheckedChange = { enabled ->
                    registerInteraction()
                    ultraSaverActive = enabled

                    if (enabled) {
                        // Ultra Saver ON → Power Saver OFF
                        powerSaverActive = false
                    }
                }
            )
        }

        Text(
            text = stringResource(id = R.string.battery_saver_ultra_desc),
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        /* ================= Info ================= */

        Text(
            text = "Battery optimizations automatically turn off after 1 hour of inactivity.",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
}
