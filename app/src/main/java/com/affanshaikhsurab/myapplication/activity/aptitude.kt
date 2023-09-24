
package com.affanshaikhsurab.myapplication.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.affanshaikhsurab.myapplication.R
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.android.synthetic.main.activity_aptitude.view.question6ChoiceA
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.json.JSONArray

class aptitude : AppCompatActivity() {

    // Define an array to store answers (initialized with zeros)
    private val answers :ArrayList<ArrayList<Int>> = ArrayList()
    private val qa:ArrayList<Int> = ArrayList()
    private var recommendations = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aptitude)

        val submitButton = findViewById<Button>(R.id.submitButton)

        submitButton.setOnClickListener {
            // Process the answers
            processAnswers()

            // You can access the answers array to get the user's choices.
            // For example, answers[0] contains the answer for question 1.
            // You can then perform further actions with the answers array.
        }
    }

    private fun processAnswers() {
        // Question 1
        answers.add(getSelectedChoice(R.id.question1RadioGroup))
        // Question 2
        answers.add(getSelectedChoice(R.id.question2RadioGroup))

        // Question 3
                answers.add( getSelectedChoice(R.id.question3RadioGroup))

        // Question 4
                answers.add( getSelectedChoice(R.id.question4RadioGroup))

        // Question 5
                answers.add( getSelectedChoice(R.id.question5RadioGroup))

        // Question 6
                answers.add(getSelectedChoice(R.id.question6RadioGroup))

        // Question 7
                answers.add(getSelectedChoice(R.id.question7RadioGroup))

        // Question 8
                answers.add( getSelectedChoice(R.id.question8RadioGroup))

        // Question 9
                answers.add( getSelectedChoice(R.id.question9RadioGroup))

        // Question 10
                answers.add(getSelectedChoice(R.id.question10RadioGroup))

                answers.add( getSelectedChoice(R.id.question10RadioGroup))

        for(questions in answers){
            for(options in questions){
                qa.add(options)
            }
        }
        Log.i("userData" , qa.toString())

        lifecycleScope.launch {
            getRecommendation()

        }
    }

    private fun getSelectedChoice(radioGroupId: Int): ArrayList<Int> {
        val list :ArrayList<Int> = ArrayList()

        val radioGroup = findViewById<RadioGroup>(radioGroupId)
        val selectedId = radioGroup.checkedRadioButtonId
        val selectedValue = radioGroup.findViewById<RadioButton>(selectedId).text

        val option = selectedValue.split(") ")[0]

            if(option.contains("a")){
                list.add(1)
                list.add(0)
                list.add(0)
                list.add(0)
                return list
            }
            if(option.contains("b")){

                list.add(0)
                list.add(1)
                list.add(0)
                list.add(0)
                return list

            }
            if(option.contains("c")){

                list.add(0)

                list.add(0)
                list.add(1)
                list.add(0)
                return list

            }
            if(option.contains("d")){

                list.add(0)

                list.add(0)

                list.add(0)
                list.add(1)
                return list

            }
        return list

    }

   suspend private fun getRecommendation(){

        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }
       var input = JSONArray(qa)
        val apiUrl =
            "https://6l1289lt-8000.inc1.devtunnels.ms/recommend"
        try {
            Log.i("apiData" , apiUrl)
            val response = client.post(apiUrl) {
                setBody(input)
                contentType(ContentType.Application.Json)
                method = HttpMethod.Post
            }
            Log.i("apiData" , response.status.value.toString())
            if (response.status == HttpStatusCode.OK) {
                Log.i("apiData", response.body())
                recommendations = response.body()
            }
        } catch (e: Exception) {
//        return "Something went wrong please try again"
        }

    }
}
