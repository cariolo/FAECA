package com.example.soke.faeca;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by soke on 27/03/2015.
 */
public class asd extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "IGPp8uUXyGziD2kGBcLPhfzk5KqYyliY3gzjH3RR", "xb0pfIjxiZgZhMgNt93b51J00HFOQTrUWe4NjJof");
    }
}
