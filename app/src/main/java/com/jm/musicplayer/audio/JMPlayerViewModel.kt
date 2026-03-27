package com.jm.musicplayer.audio

import android.app.Application
import android.content.ComponentName
import androidx.annotation.OptIn
import androidx.lifecycle.AndroidViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.jm.musicplayer.data.Song
import com.jm.musicplayer.ui.theme.VoidCyan
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.google.common.util.concurrent.MoreExecutors

@OptIn(UnstableApi::class)
class JMPlayerViewModel(application: Application) : AndroidViewModel(application) {

    private var _mediaController: MediaController? = null
    
    // UI State
    private val _currentPlayingTrackId = MutableStateFlow(-1L)
    val currentPlayingTrackId: StateFlow<Long> = _currentPlayingTrackId
    
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    init {
        // Connect to the service in the background
        val sessionToken = SessionToken(application, ComponentName(application, PlaybackService::class.java))
        val controllerFuture = MediaController.Builder(application, sessionToken).buildAsync()
        
        controllerFuture.addListener({
            _mediaController = controllerFuture.get()
            setupPlayerListener()
        }, MoreExecutors.directExecutor())
    }

    private fun setupPlayerListener() {
        _mediaController?.addListener(object : Player.Listener {
            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                // Media3 handles media_id as a string, convert back
                _currentPlayingTrackId.value = mediaItem?.mediaId?.toLongOrNull() ?: -1L
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                _isPlaying.value = isPlaying
            }
        })
    }

    /**
     * Call this when a song is tapped in the library
     */
    fun playSong(song: Song) {
        val controller = _mediaController ?: return // Return if not connected

        // Media3 requires creating a 'MediaItem' from the data path
        val mediaItem = MediaItem.Builder()
            .setMediaId(song.id.toString())
            .setUri(song.data)
            .build()
            
        // Tell the service to play this specific track
        controller.setMediaItem(mediaItem)
        controller.prepare()
        controller.play()
    }

    override fun onCleared() {
        super.onCleared()
        // Release controller connection
        _mediaController?.release()
    }
}
