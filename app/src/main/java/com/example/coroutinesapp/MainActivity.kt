package com.example.coroutinesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.coroutineContext

class MainActivity : AppCompatActivity() {
    lateinit var textView:TextView
    var advice: advicesList? = null
    lateinit var adv:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView=findViewById(R.id.textView)
        val button=findViewById<View>(R.id.button) as Button

        button.setOnClickListener {
            getAdvice(onResult = {
                advice = it
                val a=advice?.slip1?.advice.toString()
                adv=a
                CoroutineScope(IO).launch {
                    val result=useradv()
                    withContext(Main){
                        textView.text=result
                    }
                }
            })

        }
    }
    private fun getAdvice(onResult: (advicesList?) -> Unit) {
        val apiInterface = APIClient().getClinet()?.create(APIInterface::class.java)

        if (apiInterface != null) {
            apiInterface.getAdvice()?.enqueue(object : Callback<advicesList?> {
                override fun onResponse(call: Call<advicesList?>, response: Response<advicesList?>) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<advicesList?>, t: Throwable) {
                    call.cancel()
                }

            })
        }
    }
    private suspend fun useradv():String{
        return adv
    }

}