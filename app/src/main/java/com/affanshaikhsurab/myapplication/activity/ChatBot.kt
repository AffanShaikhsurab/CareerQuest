package com.affanshaikhsurab.myapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.affanshaikhsurab.myapplication.R
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
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

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

    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
    }
    val apiKey = "AIzaSyBrGAltsb1rK-HHPcEK94mqlAOfInO9CDU"
    val apiUrl =
        "https://generativelanguage.googleapis.com/v1beta2/models/chat-bison-001:generateMessage?key=$apiKey"
    var message : ArrayList<String> = ArrayList()
    var resquest_msg = message.add(userMessage)
    try {
        val response = client.post(apiUrl) {
            contentType(ContentType.Application.Json)
            setBody(resquest_msg)
            method = HttpMethod.Post
        }
        if (response.status == HttpStatusCode.OK) {
            Log.i("apiData", response.body())
            message.add(response.body())
        }
    } catch (e: Exception) {
//        return "Something went wrong please try again"
    }
    return " "
}



