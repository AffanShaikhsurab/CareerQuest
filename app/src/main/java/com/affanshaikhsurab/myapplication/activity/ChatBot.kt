package com.affanshaikhsurab.myapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.affanshaikhsurab.myapplication.R
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.apache.Apache

import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.content.TextContent
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.coroutines.launch

class ChatBot : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_bot)

        lifecycleScope.launch {
            chatBot("explain sea")
        }
    }



}

suspend fun chatBot(userMessage : String): String {

    val client = HttpClient(Apache)
    val apiKey = "AIzaSyBrGAltsb1rK-HHPcEK94mqlAOfInO9CDU"
    val apiUrl =
        "https://generativelanguage.googleapis.com/v1beta2/models/chat-bison-001:generateMessage?key=$apiKey"

    val requestContent = TextContent(
        """
        {
            "prompt": {
                "context": "You are a deep sea diver, knowledgeable about the ocean and all things in large bodies of water.\nPlease respond in concise and short sentences. Remember to shape your response as if talking to a 10 years old kid.",
                "examples": [
                    {
                        "input": {"content": "What is the deepest part of the ocean"},
                        "output": {"content": "Very good question. You must be brilliant to think about how deep the ocean can be! The deepest part of the ocean is Challenger Deep. It is located below the Pacific Ocean. If you haven't heard of the Mariana Trench, I'd check it out. Challenger Deep is located at the southern end of it!"}
                    },
                    {
                        "input": {"content": "What makes something an 'ocean' and how many of them are on Earth?"},
                        "output": {"content": "How thoughtful to be thinking about what it takes for us to call something ocean! An ocean is basically a huge basin of uninterrupted salt water on the surface of the Earth. For years, it was thought we had four oceans on Earth, but since 1999 we like to say five! Pacific, Atlantic, Arctic, Indian, and Southern."}
                    }
                ],
                "messages": [{"content": "What's the best ocean to hang out in?"}]
            },
            "temperature": 0.85,
            "top_k": 40,
            "top_p": 0.95,
            "candidate_count": 1
        }
        """.trimIndent(),
        contentType = ContentType.Application.Json
    )
    try {
        val response = client.post(apiUrl) {
            contentType(ContentType.Application.Json)
            setBody(requestContent)
            method = HttpMethod.Post
        }
        if (response.status == HttpStatusCode.OK) {
            Log.i("api", response.body())
        }
    } catch (e: Exception) {
        return "Something went wrong please try again"
    }
    return " "
}



