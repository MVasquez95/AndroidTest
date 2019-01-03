package com.example.miguel.mobiletest2

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.example.miguel.mobiletest2.adapter.MovieAdapter
import com.example.miguel.mobiletest2.model.Movie
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    private var adapter: MovieAdapter? = null
    private var movieList: List<Movie>? = null
    internal var pd: ProgressDialog
    private var swipeContainer: SwipeRefreshLayout? = null

    val activity: Activity
        get() {
            var context: Context = this
            while (context is ContextWrapper) {
                if (context is Activity) {
                    return context
                }
                context = context.baseContext
            }
            return null
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

        swipeContainer = findViewById(R.id.main_content)
        swipeContainer!!.setColorSchemeResources(android.R.color.holo_orange_dark)
        swipeContainer!!.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                initViews()
                Toast.makeText(MainActivity.this, "Todo listo", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initViews() {
        pd = ProgressDialog(this)
        pd.setMessage("Buscar Peliculas...")
        pd.setCancelable(false)
        pd.show()

        recyclerView = findViewById(R.id.recycler_view)

        movieList = ArrayList()
        adapter = MovieAdapter(this, movieList)

        if (activity.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView!!.layoutManager = GridLayoutManager(this, 2)
        } else {
            recyclerView!!.layoutManager = GridLayoutManager(this, 4)
        }

        recyclerView!!.itemAnimator = DefaultItemAnimator()
        recyclerView!!.setAdapter(adapter)
        adapter!!.notify()

        loadJSON()
    }

    private fun loadJSON() {

    }

    companion object {
        val LOG_TAG = MovieAdapter::class.java.name
    }
}