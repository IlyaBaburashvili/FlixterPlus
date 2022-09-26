package com.example.flixter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import okhttp3.Headers
import org.json.JSONArray

private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"

class TopMovieFragment : Fragment(), OnListFragmentInteractionListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.top_movie_list, container, false)
        val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar
        val recyclerView = view.findViewById<View>(R.id.list) as RecyclerView
        val context = view.context
        updateAdapter(progressBar, recyclerView)
        return view
    }

    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()

        val client = AsyncHttpClient()
        val param = RequestParams()
        param["api-key"] = API_KEY
        client[
                "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&language=en-US",
                param,
                object : JsonHttpResponseHandler()

                {
                    override fun onSuccess(
                        statusCode: Int,
                        headers: Headers,
                        json: JsonHttpResponseHandler.JSON
                    ) {
                        progressBar.hide()

                        val resultsJSON : JSONArray = json.jsonObject.get("results") as JSONArray
                        val gson = Gson()
                        var models:List<BestMovie> = ArrayList()
                        for (i in 0 until resultsJSON.length()){
                            val title : String = resultsJSON.getJSONObject(i).get("title").toString()
                            val description: String = resultsJSON.getJSONObject(i).get("overview").toString()
                            val poster: String = resultsJSON.getJSONObject(i).get("poster_path").toString()
                            models += BestMovie(title, description, poster)
                        }

                        recyclerView.adapter = BestMovieRecyclerViewAdapter(models, this@TopMovieFragment)


                        Log.d("TopMovieFragment", "response successful")
                    }


                    override fun onFailure(
                        statusCode: Int,
                        headers: Headers?,
                        errorResponse: String,
                        t: Throwable?
                    ) {
                        progressBar.hide()

                        t?.message?.let {
                            Log.e("BestMovieFragment", errorResponse)
                        }
                    }
                }]

    }

    override fun onItemClick(item: BestMovie) {
        Toast.makeText(context, "test: " + item.title, Toast.LENGTH_LONG).show()
    }

}