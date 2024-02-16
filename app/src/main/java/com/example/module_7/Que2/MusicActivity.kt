package com.example.module_7.Que2

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import com.example.module_7.R
import com.example.module_7.databinding.ActivityMusicBinding
import java.util.concurrent.TimeUnit

class MusicActivity : AppCompatActivity() {
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var handler: Handler
    private lateinit var binding : ActivityMusicBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handler = Handler()

        mediaPlayer = MediaPlayer.create(this, R.raw.music)

        binding.btnPlay.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                binding.btnPlay.text = "Play Song"
            } else {
                mediaPlayer.start()
                binding.btnPlay.text = "Pause Song"
               updateSeekBar()
            }
        }
        mediaPlayer.setOnPreparedListener {
            binding.seekBar.max = mediaPlayer.duration
        }

        // Update the current time text view and reset the player after completion
        mediaPlayer.setOnCompletionListener {
            binding.btnPlay.text = "Play Song"
            binding.seekBar.progress = 0
            binding.textViewCurrentTime.text = "0:00"
            mediaPlayer.reset()
        }

        // SeekBar change listener to handle user interaction
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun updateSeekBar() {
        // Update seek bar and current time text view every 100 milliseconds
        handler.postDelayed({
           binding.seekBar.progress = mediaPlayer.currentPosition
            binding.textViewCurrentTime.text =
                "${TimeUnit.MILLISECONDS.toMinutes(mediaPlayer.currentPosition.toLong())}" +
                        ":${TimeUnit.MILLISECONDS.toSeconds(mediaPlayer.currentPosition.toLong()) % 60}"
            updateSeekBar()
        }, 100)
    }
    override fun onDestroy() {
        mediaPlayer.release()
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }

}