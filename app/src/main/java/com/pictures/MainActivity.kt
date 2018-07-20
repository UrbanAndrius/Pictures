package com.pictures

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    private var urls = arrayListOf<String>()
    private var imageNum: Int = 0
    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.btnNext.setOnClickListener { loadImages() }

        val json = JSONArray(jsonFromFile("images.json"))
        imageNum = json.length()

        for (i in 0 until json.length()) {
            val obj = json.getJSONObject(i)
            urls.add(obj.getString("url"))
        }
        loadImages()
    }

    private fun loadImages() {
        Picasso.get().load(urls[(counter) % imageNum]).into(this.ivFirst)
        Picasso.get().load(urls[(counter + 1) % imageNum]).into(this.ivSecond)
        Picasso.get().load(urls[(counter + 2) % imageNum]).into(this.ivThird)
        counter = (counter + 1) % imageNum
    }

    private fun jsonFromFile(name: String): String {
        val json: String?

        try {
            val rawJson = this.assets.open(name)
            val size = rawJson.available()
            val buffer = ByteArray(size)
            rawJson.read(buffer)
            rawJson.close()
            json = String(buffer, Charset.forName("UTF-8"))

        } catch (ex: IOException) {
            Log.e("TAG", ex.toString())
            return ""
        }
        return json
    }
}
