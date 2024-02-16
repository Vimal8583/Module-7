package com.example.module_7.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.module_7.Model.Questions
import com.example.module_7.Que1.SplashActivity
import com.example.module_7.Que2.MusicActivity
import com.example.module_7.Que3.SongActivity
import com.example.module_7.Que4.OnlineMusicPlayActivity
import com.example.module_7.Que5.VideoActivity
import com.example.module_7.Que6.TextToSpeechActivity
import com.example.module_7.Que7.WifiActivity
import com.example.module_7.databinding.QuestionListBinding

class QuestionListAdapter(var context: Context , var quesList: MutableList<Questions>):
    RecyclerView.Adapter<QuestionListAdapter.MyViewHolder>(){
    class MyViewHolder(val binding: QuestionListBinding):RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType:Int
    ):QuestionListAdapter.MyViewHolder {
        var binding = QuestionListBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return quesList.size
    }

    override fun onBindViewHolder(holder: QuestionListAdapter.MyViewHolder, position: Int) {
        var Quest = quesList[position]
        holder.binding.tvQuest.text = "${Quest.ques}"
        holder.binding.cardView.setOnClickListener {
            when(Quest.id) {
                1 -> {
                    val intent = Intent(context, SplashActivity::class.java)
                    context.startActivity(intent)
                }

                2 -> {
                    val intent = Intent(context, MusicActivity::class.java)
                    context.startActivity(intent)
                }

                3 -> {
                    val intent = Intent(context, SongActivity::class.java)
                    context.startActivity(intent)
                }

                4-> {
                    val intent = Intent(context, OnlineMusicPlayActivity::class.java)
                    context.startActivity(intent)
                }

                5 -> {
                    val intent = Intent(context,VideoActivity::class.java)
                    context.startActivity(intent)
                }

                6 -> {
                    val intent = Intent(context, TextToSpeechActivity::class.java)
                    context.startActivity(intent)
                }

                7 -> {
                    val intent = Intent(context, WifiActivity::class.java)
                    context.startActivity(intent)
                }
            }
        }
    }

}