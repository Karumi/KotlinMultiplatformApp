package com.karumi.dribbble.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         title = "Dribbble"
        setContentView(R.layout.activity_main)
    }
}
