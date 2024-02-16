package com.example.module_7.Que3

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.module_7.R
import com.example.module_7.databinding.ActivityMusicBinding
import com.example.module_7.databinding.ActivitySongBinding
import java.io.IOException

class SongActivity : AppCompatActivity() {
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var binding: ActivitySongBinding

    private val STORAGE_PERMISSION_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkPermission()

        binding.btnSelectSong.setOnClickListener {
            selectSong()
        }

        binding.btnPlay.setOnClickListener {
            playPauseSong()
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer?.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Permission is not granted, request it
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    STORAGE_PERMISSION_CODE
                )
            } else {
                // Permission is already granted, proceed with song selection
                selectSong()
            }
        } else {
            // For devices below Android M, permission is granted by default
            // You can proceed with song selection
            selectSong()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun selectSong() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, STORAGE_PERMISSION_CODE)
    }

    private fun playPauseSong() {
        if (mediaPlayer == null) {
            // No song is playing, start playing
            getSelectedSongUri()?.let {
                playSong(it)
                binding.btnPlay.text = "Pause"
            }
        } else {
            // Song is playing, pause it
            mediaPlayer?.pause()
            binding.btnPlay.text = "Play"
        }
    }

    private fun getSelectedSongUri(): Uri? {
        return intent?.data
    }

    private fun playSong(uri: Uri?) {
        if (mediaPlayer != null) {
            mediaPlayer?.release()
            mediaPlayer = null
        }

        mediaPlayer = MediaPlayer().apply {
            if (uri != null) {
                setDataSource(this@SongActivity, uri)
            }
            prepare()
            start()

            // Update seekBar
            binding.seekBar.max = duration
            updateSeekBar()
        }
    }

    private fun updateSeekBar() {
        mediaPlayer?.let {
            binding.seekBar.progress = it.currentPosition
            if (it.isPlaying) {
                // Schedule the next update after 100 milliseconds
                binding.seekBar.postDelayed({ updateSeekBar() }, 100)
            }
        }
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == STORAGE_PERMISSION_CODE) {
            getSelectedSongUri()?.let {
                playSong(it)
            }
        }
    }
}