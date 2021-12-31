package com.example.top10downloaderapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.top10downloaderapp.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.net.URL
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var recView: RecyclerView
    lateinit var Binding: ActivityMainBinding
    var appList = ArrayList<topData>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(Binding.root)
        recView = findViewById(R.id.rvMain)
        recView.layoutManager = LinearLayoutManager(this)
        getFeedData()
    }


    fun getFeedData() {

        Binding.btnGet.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {

                val parseApplications = async {

                    FeedParser()

                }.await()
                withContext(Dispatchers.Main) {
                    recView.adapter = RVAdapter(appList)
                    recView.adapter!!.notifyDataSetChanged()
                }
            }
        }
    }

    fun FeedParser() {

        var topTenApp = ""
        var textValue = ""


        try {
            val Factory = XmlPullParserFactory.newInstance()
            val parsing = Factory.newPullParser()
            val urlTop =
                URL("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml")
            parsing.setInput(urlTop.openStream(), null)

            var Type = parsing.eventType

            while (Type != XmlPullParser.END_DOCUMENT) {

                val tag = parsing.name

                when (Type) {
                    XmlPullParser.TEXT -> {
                        textValue = parsing.text

                    }
                    XmlPullParser.END_TAG -> when (tag) {
                        "im:name" -> {
                            topTenApp = textValue
                        }

                        else -> {
                            Log.d("App10 ", "$topTenApp")
                            if(!topTenApp.isEmpty()) {

                                val App = topData(topTenApp)
                                appList.add(App)


                            }
                            topTenApp = ""
                        }
                    }
                    else -> {

                    }

                }
                Type = parsing.next()
            }

        } catch (e: XmlPullParserException) {
            e.printStackTrace()

        }

        catch(e: IOException) {
            e.printStackTrace()
        }
    }
}

class topData(topTendon: String) {
    lateinit var name : String
}