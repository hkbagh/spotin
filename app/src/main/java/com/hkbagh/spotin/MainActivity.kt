package com.hkbagh.spotin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.FlowType
import io.github.jan.supabase.auth.handleDeeplinks

val supabase = createSupabaseClient(
    supabaseUrl = "https://ilfkmpfepwtxssfstfzi.supabase.co", // Replace with your Supabase URL
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImlsZmttcGZlcHd0eHNzZnN0ZnppIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDkyNzk0ODEsImV4cCI6MjA2NDg1NTQ4MX0.71xj9cCG7RPuhn3bzNG18xvH1zqa6K8UIz9mVRBr7WM" // Replace with your Supabase anon key
) {
    install(Postgrest)
    install(Auth) {
        scheme = "spotin"
        host = "auth.supabase.com"
        flowType = FlowType.PKCE
    }
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        supabase.handleDeeplinks(intent)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) {
                v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}