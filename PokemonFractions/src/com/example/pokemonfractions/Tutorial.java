package com.example.pokemonfractions;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Tutorial extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        
        Button resetButton = ( Button )findViewById( R.id.reset );
        resetButton.setOnClickListener( startOver );
    }
	
	View.OnClickListener startOver = new View.OnClickListener() {
		
		public void onClick( View v ) {
			Intent i = new Intent( Tutorial.this, MainActivity.class );
			startActivity( i );
		}

		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			
		}
	};
	
	
	

}
