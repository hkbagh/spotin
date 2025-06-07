package com.hkbagh.spotin

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hkbagh.spotin.ui.theme.SpotinTheme
import androidx.lifecycle.lifecycleScope
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val currentSession = supabase.auth.currentSessionOrNull()
            if (currentSession == null) {
                val intent = Intent(this@HomeActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        setContent {
            SpotinTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen(
                        onLogoutClicked = {
                            lifecycleScope.launch {
                                try {
                                    supabase.auth.signOut()
                                    val intent = Intent(this@HomeActivity, LoginActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                } catch (e: Exception) {
                                    // Handle logout error
                                    e.printStackTrace()
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreen(onLogoutClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome!", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onLogoutClicked) {
            Text("Logout")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SpotinTheme {
        HomeScreen(onLogoutClicked = {})
    }
} 