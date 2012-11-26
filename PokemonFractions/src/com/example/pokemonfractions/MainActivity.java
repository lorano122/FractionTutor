package com.example.pokemonfractions;

import android.os.Bundle;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Button maleButton = ( Button )findViewById( R.id.male );
        Button femaleButton = ( Button )findViewById( R.id.female );
        maleButton.setOnClickListener( tutorial );
        femaleButton.setOnClickListener( tutorial );
    }
    
    View.OnClickListener tutorial = new View.OnClickListener() {
		
		public void onClick( View v ) {
			Intent i = new Intent( MainActivity.this, Tutorial.class );
			startActivity( i );
		}

		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			
		}
	};

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
	
}
