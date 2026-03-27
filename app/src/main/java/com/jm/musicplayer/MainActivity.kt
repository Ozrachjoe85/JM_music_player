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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jm.musicplayer.audio.JMPlayerViewModel
import com.jm.musicplayer.data.MusicScanner
import com.jm.musicplayer.data.Song
import com.jm.musicplayer.ui.screens.LibraryScreen
import com.jm.musicplayer.ui.theme.JMMusicPlayerTheme
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : ComponentActivity() {

    private val viewModel: JMPlayerViewModel by viewModels()
    private val scanner by lazy { MusicScanner(this) }
    private val songsState = MutableStateFlow<List<Song>>(emptyList())

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
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_AUDIO)
        } else {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        setContent {
            JMMusicPlayerTheme {
                val navController = rememberNavController()
                val songs by songsState.collectAsState()
                val currentTrackId by viewModel.currentPlayingTrackId.collectAsState()

                // Surface fillMaxSize ensures we use the whole screen
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = "library") {
                        composable("library") {
                            LibraryScreen(
                                songs = songs,
                                currentTrackId = currentTrackId,
                                onSongClicked = { song -> 
                                    viewModel.playSong(song)
                                    navController.navigate("now_playing")
                                }
                            )
                        }
                        composable("now_playing") {
                            // Temporary placeholder to prevent crash until next file is added
                            Box(modifier = Modifier.fillMaxSize()) {
                                Text(
                                    text = "NOW PLAYING SCREEN",
                                    modifier = Modifier.align(androidx.compose.ui.Alignment.Center),
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Button(
                                    onClick = { navController.popBackStack() },
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Text("Back to Library")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun refreshLibrary() {
        songsState.value = scanner.scanAudioFiles()
    }
}
