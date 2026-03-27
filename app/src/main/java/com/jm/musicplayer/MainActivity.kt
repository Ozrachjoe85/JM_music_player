package com.jm.musicplayer

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.jm.musicplayer.audio.JMPlayerViewModel
import com.jm.musicplayer.data.MusicScanner
import com.jm.musicplayer.data.Song
import com.jm.musicplayer.ui.screens.LibraryScreen
import com.jm.musicplayer.ui.theme.JMMusicPlayerTheme
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : ComponentActivity() {

    // JMPlayer ViewModel handles audio operations
    private val viewModel: JMPlayerViewModel by viewModels()
    private val scanner by lazy { MusicScanner(this) }
    
    // UI state for songs (starts empty)
    private val songsState = MutableStateFlow<List<Song>>(emptyList())

    // Modern permission request handler
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                refreshLibrary()
            } else {
                Toast.makeText(this, "Permissions required to view music.", Toast.LENGTH_LONG).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initial permission check (simple flow)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13+
            requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_AUDIO)
        } else {
            // Android 12 and below
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        setContent {
            JMMusicPlayerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val songs by songsState.collectAsState()
                    val currentTrackId by viewModel.currentPlayingTrackId.collectAsState()

                    // Call the Library Screen (We will create this file next)
                    LibraryScreen(
                        songs = songs,
                        currentTrackId = currentTrackId,
                        onSongClicked = { song -> viewModel.playSong(song) }
                    )
                }
            }
        }
    }

    private fun refreshLibrary() {
        // Scans on the main thread for simplicity in skeleton
        songsState.value = scanner.scanAudioFiles()
    }
}
