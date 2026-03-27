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

@Composable
fun LibraryScreen(
    songs: List<Song>,
    currentTrackId: Long,
    onSongClicked: (Song) -> Unit
) {
    // Column with fillMaxSize and system bar padding prevents content clipping
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "JM LIBRARY",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = VoidCyan,
            modifier = Modifier.padding(top = 24.dp, bottom = 16.dp)
        )
        
        if (songs.isEmpty()) {
            Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                Text(
                    text = "No tracks found on device.",
                    modifier = Modifier.align(androidx.compose.ui.Alignment.Center),
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        } else {
            // LazyColumn with weight(1f) ensures it fits exactly between the title and bottom edge
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
                items(songs, key = { it.id }) { song ->
                    val isPlayingThis = song.id == currentTrackId
                    
                    VoidGlassCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSongClicked(song) }
                    ) {
                        Column {
                            Text(
                                text = song.title,
                                color = if (isPlayingThis) VoidCyan else MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.SemiBold,
                                maxLines = 1
                            )
                            Text(
                                text = song.artist,
                                color = if (isPlayingThis) VoidCyan.copy(alpha = 0.7f) else MaterialTheme.colorScheme.secondary,
                                style = MaterialTheme.typography.bodySmall,
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }
    }
}
