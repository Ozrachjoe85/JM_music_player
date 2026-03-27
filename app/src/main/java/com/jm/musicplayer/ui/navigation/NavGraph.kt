package com.jm.musicplayer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jm.musicplayer.audio.JMPlayerViewModel
import com.jm.musicplayer.data.Song
import com.jm.musicplayer.ui.screens.LibraryScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: JMPlayerViewModel,
    songs: List<Song>
) {
    val currentTrackId by viewModel.currentPlayingTrackId.collectAsState()

    NavHost(
        navController = navController,
        startDestination = "library"
    ) {
        composable("library") {
            LibraryScreen(
                songs = songs,
                currentTrackId = currentTrackId,
                onSongClicked = { song -> 
                    viewModel.playSong(song)
                    // Auto-navigate to Now Playing when a song starts
                    navController.navigate("now_playing")
                }
            )
        }
        composable("now_playing") {
            // We will build this screen in the next step!
            // For now, it's a placeholder so the app doesn't crash
        }
        composable("equalizer") {
            // Placeholder for EQ
        }
    }
}
