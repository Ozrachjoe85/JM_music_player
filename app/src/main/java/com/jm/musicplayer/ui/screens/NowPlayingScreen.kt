package com.jm.musicplayer.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jm.musicplayer.data.Song
import com.jm.musicplayer.ui.theme.VoidCyan
import com.jm.musicplayer.ui.theme.VoidPurple

@Composable
fun NowPlayingScreen(
    song: Song?,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(VoidPurple.copy(alpha = 0.2f), Color.Black)
                )
            )
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = VoidCyan)
            }
            Text(
                text = "NOW PLAYING",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelLarge,
                color = VoidCyan,
                letterSpacing = 2.sp
            )
            Spacer(modifier = Modifier.width(48.dp)) // Balance the back button
        }

        Spacer(modifier = Modifier.weight(0.1f))

        // Cybernetic Record/Art Placeholder
        Box(
            modifier = Modifier
                .size(280.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.sweepGradient(
                        listOf(VoidCyan, VoidPurple, VoidCyan)
                    ),
                    alpha = 0.1f
                )
                .padding(2.dp)
                .clip(CircleShape)
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            // Neon Glow Center
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.radialGradient(
                            listOf(VoidCyan.copy(alpha = 0.4f), Color.Transparent)
                        )
                    )
            )
            Text(
                text = "JM",
                style = MaterialTheme.typography.displayMedium,
                color = VoidCyan,
                fontWeight = FontWeight.Black
            )
        }

        Spacer(modifier = Modifier.weight(0.1f))

        // Track Info
        Text(
            text = song?.title ?: "No Track Selected",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
        Text(
            text = song?.artist ?: "Unknown Artist",
            style = MaterialTheme.typography.bodyLarge,
            color = VoidCyan,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Simple Controls Placeholder
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // We will add logic to these later
            Text("⏮", color = VoidCyan, fontSize = 32.sp)
            
            Surface(
                modifier = Modifier.size(72.dp),
                shape = CircleShape,
                color = VoidCyan,
                shadowElevation = 8.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        Icons.Default.PlayArrow,
                        contentDescription = "Play",
                        tint = Color.Black,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
            
            Text("⏭", color = VoidCyan, fontSize = 32.sp)
        }

        Spacer(modifier = Modifier.weight(0.2f))
    }
}
