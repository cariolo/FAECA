package com.example.soke.faeca;

import android.app.Application;

import com.parse.Parse;

public class Inicializacion extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "IGPp8uUXyGziD2kGBcLPhfzk5KqYyliY3gzjH3RR", "xb0pfIjxiZgZhMgNt93b51J00HFOQTrUWe4NjJof");

    }
}
