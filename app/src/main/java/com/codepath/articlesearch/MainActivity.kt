package com.codepath.articlesearch

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.articlesearch.databinding.ActivityMainBinding
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import kotlinx.serialization.json.Json
import okhttp3.Headers
import org.json.JSONException

fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    useAlternativeNames = false
}

private const val TAG = "MainActivity/"
private const val SEARCH_API_KEY = BuildConfig.API_KEY
private const val ARTICLE_SEARCH_URL =
    "https://api.themoviedb.org/3/person/popular?api_key=${SEARCH_API_KEY}"+"&language=en-US&page=1"

class MainActivity : AppCompatActivity() {
    private lateinit var peopleRecyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding
    private val peopleList = mutableListOf<Person>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // inflates the layout and binds to main
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // sets up recycler view for people and sets to adapter (where adapter will feed vals)
        peopleRecyclerView = findViewById(R.id.people)
        val articleAdapter = ArticleAdapter(this, peopleList)
        peopleRecyclerView.adapter = articleAdapter


        peopleRecyclerView.layoutManager = LinearLayoutManager(this).also {
            val dividerItemDecoration = DividerItemDecoration(this, it.orientation)
            peopleRecyclerView.addItemDecoration(dividerItemDecoration)
        }

        val client = AsyncHttpClient()
        client.get(ARTICLE_SEARCH_URL, object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "Failed to fetch people: $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.i(TAG, "Successfully fetched people: $json")
                try {
                    // Do something with the returned json (contains article information)
                    val parsedJson = createJson().decodeFromString(
                        SearchResponse.serializer(),
                        json.jsonObject.toString()
                    )
                    // Save the articles
                    parsedJson.results?.let { list ->
                        peopleList.addAll(list)
                        // Reload the screen
                        articleAdapter.notifyDataSetChanged()
                    }
                } catch (e: JSONException) {
                    Log.e(TAG, "Exception: $e")
                }
            }

        })

    }
}