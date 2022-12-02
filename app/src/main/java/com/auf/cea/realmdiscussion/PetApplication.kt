package com.auf.cea.realmdiscussion

import android.app.Application
import io.realm.Realm

class PetApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }

}