package com.proyekakhir.event.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.proyekakhir.event.R
import com.proyekakhir.event.di.viewModule
import org.koin.core.context.loadKoinModules


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadKoinModules(viewModule)
        supportActionBar?.title = "Events"
        supportActionBar?.setHomeAsUpIndicator(com.proyekakhir.testsuitmedia.R.mipmap.ic_back_white)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

    }




}