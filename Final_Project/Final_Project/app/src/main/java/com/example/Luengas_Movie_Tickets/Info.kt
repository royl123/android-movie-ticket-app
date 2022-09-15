package com.example.Luengas_Movie_Tickets;

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class info : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.info)

        // Set up dummy data for screening time spinner
        val times = arrayOf("9:00am","12:00pm","3:00pm","6:00pm", "9:00pm")
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, times)
        val spinner = findViewById<Spinner>(R.id.selectTimes)
        // display screening time selection
        val confirming = findViewById<TextView>(R.id.confirming)

        // set up + - buttons for ticket counter
        val plus = findViewById<Button>(R.id.plus)
        val minus = findViewById<Button>(R.id.minus)
        val plus2 = findViewById<Button>(R.id.plus2)
        val minus2 = findViewById<Button>(R.id.minus2)
        val general = findViewById<TextView>(R.id.general)
        val kids = findViewById<TextView>(R.id.kids)
        var genCount = 0
        var kidCount = 0
        val button = findViewById<Button>(R.id.toCheckout)

        plus.setOnClickListener{
            genCount+=1
            general.text = genCount.toString()
        }
        minus.setOnClickListener{
            genCount-=1
            general.text = genCount.toString()
        }
        plus2.setOnClickListener{
            kidCount+=1
            kids.text = kidCount.toString()
        }
        minus2.setOnClickListener{
            kidCount-=1
            kids.text = kidCount.toString()
        }

        // transfer over clicked title and poster from Home Screen
        val moviePick = findViewById<TextView>(R.id.moviePick)
        moviePick.text= intent.getStringExtra("title").toString();
        val moviePickPosterUrl = intent.getStringExtra("poster").toString();
        val poster = findViewById<ImageView>(R.id.moviePoster)
        Glide.with(this).load(moviePickPosterUrl).into(poster);

        // Handle activity lifecycle limitations - because it erases title + poster on Back press
        if(moviePick.text == "null") {
            moviePick.text = "Selected Movie"
            Glide.with(this).load(R.drawable.placeholder).into(poster);
        }

        // display screening time selection from spinner
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object :
        AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                confirming.text = "You selected the "+times[p2]+" screening"
            }
        }

        // calculate total based on # of tickets from each view/box
        button.setOnClickListener{
            val intent = Intent(this, Checkout::class.java)
            startActivity(intent)
            val numbKidTickets = kids.text as String
            val numbGenTickets = general.text as String
            val generalTotal = Integer.parseInt(numbGenTickets) * 14.75
            val kidTotal = Integer.parseInt(numbKidTickets) * 11
            val grandTotal = (generalTotal + kidTotal)

        // Transfer over data to last screen containing summary
            val extras = Bundle()
            extras.putString("movieSelected", moviePick.text.toString())
            extras.putString("timeSelected", confirming.text as String?)
            extras.putDouble("grandtotal", grandTotal)
            extras.putString("numbGenTickets", (generalTotal/14.75).toInt().toString())
            extras.putString("numbKidTickets", (kidTotal/11).toString())
            intent.putExtras(extras)
            startActivity(intent)

        }
    }
}