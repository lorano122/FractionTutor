package com.example.pokemonfractions;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class FlingScore extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fling_score);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
        setScoreText();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_fling_score, menu);
        return true;
    }
    
    public void setScoreText()
    {
    	TextView out = (TextView)findViewById(R.id.result);
    	Intent intent = getIntent();
    	String result = intent.getExtras().getString("results");
    	String toDisplay = "Congradulations!\nIt took"+result+"pokeballs\nfor you capture 6 pokemon";
    	out.setText(toDisplay);
    }
    
    public void playFling(View view)
    {
    	Intent fling = new Intent(this, PokeFling.class);
    	startActivity(fling);
    }
    
    public void playAddition(View view)
    {
    	Intent fling = new Intent(this, MainActivity.class);
    	startActivity(fling);
    }
}
