package net.bruty.un_motivational_quotes

import android.app.Activity
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import me.relex.circleindicator.CircleIndicator3
import okhttp3.*
import java.io.IOException
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var quotesList: List<Quote>
    private lateinit var activity: Activity

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loading)

        supportActionBar?.hide()
        val window: Window = this@MainActivity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        activity = this

        fetchJson()
    }

    fun fetchJson() {
        val url = "https://uninspired-quotes.herokuapp.com/api/quote"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("net.bruty", "Failed to fetch")
            }

            override fun onResponse(call: Call, response: Response) {
                var body = ""
                response.body?.string()?.let { body = it }
                quotesList = Gson().fromJson(body, Array<Quote>::class.java).asList()
                activity.runOnUiThread(Runnable {
                    setContentView(R.layout.activity_main)
                    val viewPager2 = findViewById<ViewPager2>(R.id.view_pager2)
                    viewPager2.adapter = ViewPageAdapter(quotesList)

                    val indicator = findViewById<CircleIndicator3>(R.id.indicator)
                    indicator.setViewPager(viewPager2)

                    val fab: View = findViewById(R.id.floatingActionButton)
                    if(!fab.hasOnClickListeners()) {
                        fab.setOnClickListener {
                            fetchJson()
                        }
                    }
                })
            }

        })
    }
}