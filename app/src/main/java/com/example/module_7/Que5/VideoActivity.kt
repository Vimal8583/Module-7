package com.example.module_7.Que5

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PowerManager
import android.provider.MediaStore
import android.view.WindowManager
import android.widget.SeekBar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.module_7.R
import com.example.module_7.databinding.ActivitySongBinding
import com.example.module_7.databinding.ActivityVideoBinding
import java.io.File

class VideoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVideoBinding
    private val externalStoragePermissionCode = 101
    private val videoPickCode = 102
    private lateinit var wakeLock: PowerManager.WakeLock

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkExternalStoragePermission()


        binding.pickVideoButton.setOnClickListener {
            pickVideo()
        }

        // Initialize WakeLock
        val powerManager = getSystemService(POWER_SERVICE) as PowerManager
        wakeLock = powerManager.newWakeLock(
            PowerManager.SCREEN_BRIGHT_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP,
            "YourApp:VideoPlayerWakeLock"
        )

        // Set up seek bar listener
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    binding.videoView.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        // Set up play/pause button click listener
        binding.playPauseButton.setOnClickListener {
            if (binding.videoView.isPlaying) {
                binding.videoView.pause()
                binding.playPauseButton.setImageResource(R.drawable.baseline_play_arrow_24)
            } else {
                binding.videoView.start()
                binding.playPauseButton.setImageResource(R.drawable.baseline_pause_24)
            }
        }

        // Start playing the video
        binding.videoView.start()

        // Acquire WakeLock to keep the screen on
        acquireWakeLock()
    }

    private fun pickVideo() {

        val intent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
        intent.type = "video/*"
        startActivityForResult(intent, videoPickCode)
    }

    override fun onDestroy() {
        super.onDestroy()

        // Release the WakeLock when the activity is destroyed
        releaseWakeLock()
    }

    private fun acquireWakeLock() {
        if (!wakeLock.isHeld) {
            wakeLock.acquire()
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

    private fun releaseWakeLock() {
        if (wakeLock.isHeld) {
            wakeLock.release()
            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == videoPickCode && resultCode == RESULT_OK && data != null) {
            val selectedVideoUri: Uri = data.data ?: return
            val videoPath = getRealPathFromUri(selectedVideoUri)

            // Set the selected video path to VideoView
            binding.videoView.setVideoPath(videoPath)

            // Start playing the selected video
            binding.videoView.start()

            // Acquire WakeLock to keep the screen on
            acquireWakeLock()
        }
    }


    private fun checkExternalStoragePermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_DENIED
        ) {
            // Permission not granted, request it
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                externalStoragePermissionCode
            )
        }
    }

    private fun getRealPathFromUri(uri: Uri): String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, projection, null, null, null)
        val columnIndex = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor?.moveToFirst()
        val path = cursor?.getString(columnIndex ?: 0) ?: ""
        cursor?.close()
        return path
    }
}