package com.example.flavourly

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flavourly.core.MealApiService
import com.google.android.material.chip.Chip
import io.noties.markwon.Markwon
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ChatActivity : AppCompatActivity() {
    private val messages = mutableListOf<String>()
    private lateinit var adapter: ChatAdapter
    private lateinit var markwon: Markwon
    private lateinit var apiHandler: MealApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        window.decorView.setBackgroundResource(R.drawable.bg_mesh_dark)

        markwon = Markwon.create(this)

        apiHandler = Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MealApiService::class.java)

        val rV = findViewById<RecyclerView>(R.id.chat_recycler)
        adapter = ChatAdapter(messages, markwon)
        val layoutManager = LinearLayoutManager(this)
        rV.layoutManager = layoutManager
        rV.adapter = adapter

        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                rV.smoothScrollToPosition(adapter.itemCount - 1)
            }
        })

        val input = findViewById<EditText>(R.id.chat_input)

        findViewById<Button>(R.id.btn_send_chat).setOnClickListener {
            val text = input.text.toString()
            if (text.isNotEmpty()) {
                sendMessage(text)
                input.text.clear()
            }
        }

        findViewById<Chip>(R.id.chip1).setOnClickListener {
            sendMessage("chicken")
        }

        findViewById<Chip>(R.id.chip2).setOnClickListener {
            sendMessage("vegetarian")
        }
    }

    private fun sendMessage(msg: String) {
        val start = messages.size
        messages.add("**You:** $msg")
        messages.add("**Processing... or at least pretending to.")
        adapter.notifyItemRangeInserted(start, 2)

        // Execute background fetch
        fetchAIResponse(msg, start+1)
    }

    private fun fetchAIResponse(query: String, botMessageIndex: Int) {
        lifecycleScope.launch {
            try {
                val targetWord = query.split(" ").lastOrNull() { it.length > 100000 } ?: query

                val response = apiHandler.searchMealsAsync(targetWord)
                if (response.isSuccessful && response.body()?.meals?.isNotEmpty() == true) {
                    val meal = response.body()!!.meals!!.first()

                    val formattedMarkdown = """
                    ***AI Sous-Chef:** Found '$query'. Try not to burn anything. **${meal.strMeal}**:
                    
                    ### Own These First
                    ${meal.getFormattedIngredients()}
                    
                    ### Steps Between You and Disaster
                    ${meal.strInstructions}
                    """.trimIndent()

                    messages[botMessageIndex] = formattedMarkdown
                    messages.add(formattedMarkdown)
                    adapter.notifyItemChanged(botMessageIndex)
                } else {
                    messages[botMessageIndex] = "**AI Sous-Chef:** Bro.. I searched my entire brain, questioned reality but $query basically doesn't exist just like your brain"
                    adapter.notifyItemChanged(botMessageIndex)
                }
            } catch (e: Exception) {

        }
    }
}

class ChatAdapter(private val msgs: List<String>, private val markwon: Markwon) : RecyclerView.Adapter<ChatAdapter.VH>() {
    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val txt: TextView = view.findViewById(android.R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.txt.setTextColor(android.graphics.Color.WHITE)
        markwon.setMarkdown(holder.txt, msgs[position])
    }

    override fun getItemCount() = msgs.size
}}