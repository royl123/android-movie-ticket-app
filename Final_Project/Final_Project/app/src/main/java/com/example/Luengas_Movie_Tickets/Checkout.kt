package com.example.Luengas_Movie_Tickets

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*


class Checkout : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.checkout)

        val movieSelected = findViewById<TextView>(R.id.movieSelected)
        val timeSelected = findViewById<TextView>(R.id.timeSelected)
        val grandTotal = findViewById<TextView>(R.id.grandtotal)
        val receipt = findViewById<TextView>(R.id.receipt)
        val buy = findViewById<TextView>(R.id.buy)

        // Transferring over data from info (2nd) screen
        val intent = intent
        val extras = intent.extras
        movieSelected.text = "Watching: "+"'"+extras!!.getString("movieSelected")+"'"
        timeSelected.text = extras!!.getString("timeSelected")
        grandTotal.text = "General Admission ticket(s): "+extras!!.getString("numbGenTickets")+"\n"+"Child ticket(s): "+extras!!.getString("numbKidTickets")+"\n"+"Your total is $"+extras!!.getDouble("grandtotal").toString()
        val sdf = SimpleDateFormat("M/dd/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        receipt.setVisibility(View.INVISIBLE)

        //Show receipt view when clicking buy ticket(s) button
        buy.setOnClickListener{
            receipt.text = "PURCHASE CONFIRMED ON: "+"\n"+currentDate
            receipt.setVisibility(View.VISIBLE)

            // *Attempted* due to Android limitations (activity lifecycle)
            // populate spinner with update that purchase click was made
            val purchasesInit = arrayOf("")
            val purchaseAfter =arrayOf("Bought 1")
            val simpleListView = findViewById<View>(R.id.purchaseHistory) as Spinner
            var arrayAdapter: ArrayAdapter<String>? = ArrayAdapter<String>(
                this@Checkout,
                android.R.layout.simple_spinner_item, purchasesInit
            )
            simpleListView.adapter = arrayAdapter
                   arrayAdapter = ArrayAdapter<String>(
                    this@Checkout,
                    android.R.layout.simple_spinner_item, purchaseAfter
                )
                simpleListView.adapter = arrayAdapter
        }
    }
}

