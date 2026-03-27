package com.jm.musicplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jm.musicplayer.data.MusicScanner
import com.jm.musicplayer.data.Song
import com.jm.musicplayer.ui.components.VoidGlassCard
import com.jm.musicplayer.ui.theme.JMMusicPlayerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // In a real app, we'd check permissions properly, 
        // but for the skeleton, we'll initialize the scanner.
        val scanner = MusicScanner(this)
        
        setContent {
            JMMusicPlayerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val songs = remember { scanner.scanAudioFiles() }
                    LibraryScreen(songs)
                }
            }
        }
    }
}

@Composable
fun LibraryScreen(songs: List<Song>) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "LIBRARY",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(songs) { song ->
                VoidGlassCard(modifier = Modifier.fillMaxWidth()) {
                    Column {
                        Text(text = song.title, color = MaterialTheme.colorScheme.primary)
                        Text(text = song.artist, color = MaterialTheme.colorScheme.secondary, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}
