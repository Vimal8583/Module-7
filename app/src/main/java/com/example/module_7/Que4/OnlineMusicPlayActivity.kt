package com.example.module_7.Que4

import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.module_7.R
import com.example.module_7.databinding.ActivityOnlineMusicPlayBinding

class OnlineMusicPlayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnlineMusicPlayBinding

    lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnlineMusicPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mediaPlayer = MediaPlayer()

        binding.idIBPlay.setOnClickListener {

            // on below line we are creating a variable for our audio url
            var audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"

            // on below line we are setting audio stream
            // type as stream music on below line.
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)

            // on below line we are running a try
            // and catch block for our media player.
            try {
                // on below line we are setting audio
                // source as audio url on below line.
                mediaPlayer.setDataSource(audioUrl)

                // on below line we are
                // preparing our media player.
                mediaPlayer.prepare()

                // on below line we are
                // starting our media player.
                mediaPlayer.start()

            } catch (e: Exception) {

                // on below line we are handling our exception.
                e.printStackTrace()
            }
            // on below line we are displaying a toast message as audio player.
            Toast.makeText(applicationContext, "Audio started playing..", Toast.LENGTH_SHORT).show()

        }

        binding.idIBPause.setOnClickListener {
            // on below line we are checking
            // if media player is playing.
            if (mediaPlayer.isPlaying) {
                // if media player is playing we
                // are stopping it on below line.
                mediaPlayer.stop()

                // on below line we are resetting
                // our media player.
                mediaPlayer.reset()

                // on below line we are calling
                // release to release our media player.
                mediaPlayer.release()

                // on below line we are displaying a toast message to pause audio/
                Toast.makeText(applicationContext, "Audio has been  paused..", Toast.LENGTH_SHORT)
                    .show()

            } else {
                // if audio player is not displaying we are displaying below toast message
                Toast.makeText(applicationContext, "Audio not played..", Toast.LENGTH_SHORT).show()
            }

        }
    }
}