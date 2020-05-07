package com.example.back4app.userregistrationexample;

import com.parse.Parse;

import android.app.Application;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("NZvEw50Av2PBlqMTFM3N5EoKWKSVfHaGvhSmGKi4")
                // if desired
                .clientKey("KMKgsxK4TzG4Zx50DH52lM3ZiFzfGSg2MY9LwVHJ")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
