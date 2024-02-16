package com.example.module_7.Que6

import android.net.http.UrlRequest.Status
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import com.example.module_7.R
import com.example.module_7.databinding.ActivityTextToSpeechBinding
import java.util.Locale

class TextToSpeechActivity : AppCompatActivity() {
    private lateinit var textToSpeech : TextToSpeech
    private lateinit var binding : ActivityTextToSpeechBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTextToSpeechBinding.inflate(layoutInflater)
        setContentView(binding.root)

        textToSpeech = TextToSpeech(this){
            if (it == TextToSpeech.SUCCESS){
                val result = textToSpeech.setLanguage(Locale.getDefault())
                if (result == TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED
                ) {
                    Toast.makeText(this, "Language not Found", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Handle initialization error
            }
            }

        binding.buttonSpeak.setOnClickListener {
            val textToRead = binding.editText.text.toString()
            textToSpeech.speak(textToRead, TextToSpeech.QUEUE_FLUSH, null, null)
        }
        }
    override fun onDestroy() {
        if (textToSpeech.isSpeaking) {
            textToSpeech.stop()
        }
        textToSpeech.shutdown()
        super.onDestroy()
    }
    }
