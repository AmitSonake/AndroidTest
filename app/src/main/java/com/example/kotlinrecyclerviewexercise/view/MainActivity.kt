package com.example.kotlinrecyclerviewexercise.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinrecyclerviewexercise.R
import com.example.kotlinrecyclerviewexercise.adapter.DynamicListAdapter
import com.example.kotlinrecyclerviewexercise.model.FactsFeed
import com.example.kotlinrecyclerviewexercise.model.SingletonClient
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title= "Loading...."
        //read json data to load recyclerview

        fetchJson()

        //** Set the colors of the Pull To Refresh View
        swipe_refresh.setOnRefreshListener { // Your code to refresh the list here.
            fetchJson()
        }
        // Configure the refreshing colors
        swipe_refresh.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )

    }

    private fun fetchJson(){
        val url: String ="https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/facts.json"
        val request = Request.Builder().url(url).build()
        val client = SingletonClient.clientInstance // reading singleton instance of okhttp client class
        client.newCall(request).enqueue(object :Callback{
            override fun onResponse(call: Call, response: Response) {
                val body =response.body()?.string()
                val gSon = GsonBuilder().create()
                val factsFeed = gSon.fromJson(body,
                    FactsFeed::class.java)

                runOnUiThread {
                    supportActionBar?.title= factsFeed.title
                    recyclerview_main_activity.adapter=
                        DynamicListAdapter(
                            factsFeed
                        )
                    swipe_refresh.isRefreshing = false
                }

            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request")
            }
        })
    }
}




