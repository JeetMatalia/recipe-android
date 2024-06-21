package com.example.RecipeApp

import android.app.Application
import androidx.room.Room
import com.example.RecipeApp.db.RecipeDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    companion object {
        lateinit var database: RecipeDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(applicationContext, RecipeDatabase::class.java, "recipe_database")
            .build()
    }
}
