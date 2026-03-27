package com.jm.musicplayer.data

import android.content.Context
import android.provider.MediaStore

data class Song(
    val id: Long,
    val title: String,
    val artist: String,
    val data: String,
    val duration: Long
)

class MusicScanner(private val context: Context) {
    fun scanAudioFiles(): List<Song> {
        val songList = mutableListOf<Song>()
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DURATION
        )

        // Only select files where duration is greater than 0
        val selection = "${MediaStore.Audio.Media.DURATION} > 0"

        context.contentResolver.query(uri, projection, selection, null, null)?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
            val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)

            while (cursor.moveToNext()) {
                songList.add(
                    Song(
                        id = cursor.getLong(idColumn),
                        title = cursor.getString(titleColumn) ?: "Unknown Title",
                        artist = cursor.getString(artistColumn) ?: "Unknown Artist",
                        data = cursor.getString(dataColumn),
                        duration = cursor.getLong(durationColumn)
                    )
                )
            }
        }
        return songList
    }
}
