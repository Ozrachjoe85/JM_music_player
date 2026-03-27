package com.jm.musicplayer.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jm.musicplayer.data.Song
import com.jm.musicplayer.ui.components.VoidGlassCard
import com.jm.musicplayer.ui.theme.VoidCyan
import com.jm.musicplayer.ui.theme.VoidPurple

@Composable
fun LibraryScreen(
    songs: List<Song>,
    currentTrackId: Long,
    onSongClicked: (Song) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        // Aesthetic Title
        Text(
            text = "JM LIBRARY",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        
        if (songs.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "No tracks found.",
                    modifier = Modifier.align(androidx.compose.ui.Alignment.Center),
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(songs, key = { it.id }) { song ->
                // Check if this song is currently playing
                val isPlayingThis = song.id == currentTrackId
                
                VoidGlassCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        // Highlight the playing track with Cyan
                        .then(if (isPlayingThis) Modifier.clickable { onSongClicked(song) } else Modifier.clickable { onSongClicked(song) }),
                    content = {
                        Column {
                            Text(
                                text = song.title,
                                color = if (isPlayingThis) VoidCyan else MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = song.artist,
                                color = if (isPlayingThis) VoidCyan.copy(alpha = 0.7f) else MaterialTheme.colorScheme.secondary,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                )
            }
        }
    }
}
