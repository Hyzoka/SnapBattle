package com.test.snapbattle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.test.core.SnapBattleAppTheme
import com.test.features.navigation.AppNavigation
import com.test.snapbattle.ui.theme.SnapBattleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SnapBattleAppTheme {
                AppNavigation()
            }
        }
    }
}
