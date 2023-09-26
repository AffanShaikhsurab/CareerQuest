package com.affanshaikhsurab.myapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.withCreated
import androidx.recyclerview.widget.RecyclerView
import com.affanshaikhsurab.myapplication.R
import com.affanshaikhsurab.myapplication.adapter.CareerAdapter
import com.google.gson.Gson
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation

import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.content.TextContent
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.json.JSONArray

class ChatBot : AppCompatActivity() {
    val message: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_bot)


        val send : Button = findViewById(R.id.sendButton)
        val msg :EditText = findViewById(R.id.messageInput)
        val  recylerview:ListView = findViewById(R.id.chatListView)
        send.setOnClickListener {
            if(!msg.text.isNullOrEmpty()){
                lifecycleScope.launch {
                    withContext(Dispatchers.IO){
                        chatBot(msg.text.toString())
                        val adapter = (this, R.layout.list, message)
                        recylerview.adapter = adapter

                    }
                }
            }
        }





    }
    suspend fun chatBot(userMessage : String): String {

        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }


        message.add("You:::" + userMessage) // Add your strings to the ArrayList
        val input = Gson().toJson(ChatInput(message))
        val apiUrl =
            "https://6l1289lt-3000.inc1.devtunnels.ms/ask"


        Log.i("apiData", input)

        try {
            val response = client.post(apiUrl) {
                setBody(input)
                contentType(ContentType.Application.Json)
            }

            var msg = response.body<String>()
            var output = Gson().fromJson(msg , ChatOuput::class.java)
            var response_output =  output.response

            Log.i("apiData",response_output)

                message.add("Bot::" + response_output)

        } catch (e: Exception) {
//        return "Something went wrong please try again"
        }
        return " "
    }





}
data class ChatInput(val message : ArrayList<String>)

data class ChatOuput(val response : String)


