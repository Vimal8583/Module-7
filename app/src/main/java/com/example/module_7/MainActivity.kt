package com.example.module_7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.module_7.Adapter.QuestionListAdapter
import com.example.module_7.Model.Questions
import com.example.module_7.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var questionList = mutableListOf<Questions>()
    private lateinit var questionAdapter: QuestionListAdapter
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prepareData()
        questionAdapter = QuestionListAdapter(this,questionList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = questionAdapter
    }
        private fun prepareData() {
            questionList.add(
                Questions(
                    1, "1.set animation on splash screen with app logo.")
            )
            questionList.add(
                Questions(2,"2.Create an application to play song from raw resource folder.")
            )
            questionList.add(
                Questions(3, "3.Create an application to play song from mobile memory .")
            )
            questionList.add(
                Questions(4, "4.Create an application to play song from Server.")
            )
            questionList.add(
                Questions(5, "5.use WAKE LOCK when playing video play .")
            )
            questionList.add(
                Questions(6, "6.Create an application to convert text typed in edit text into speech.")
            )
            questionList.add(
                Questions(7,"7.Create an application for Wi-Fi on-off.")
            )
        }
}