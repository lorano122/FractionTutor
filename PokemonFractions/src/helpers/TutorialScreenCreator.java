package helpers;

import java.util.ArrayList;

import com.example.pokemonfractions.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class TutorialScreenCreator {
	
	private Paint p;
	private Canvas canvas;
	
	public TutorialScreenCreator( Paint p, Canvas canvas)
	{
		this.p = p;
		this.canvas = canvas;
	}
	
	
	public void createFlingScreen(Bitmap fraction, Bitmap ball)
	{
		 
		p.setColor(Color.WHITE);
    	canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), p);
    	p.setTextSize(30);
    	p.setColor(Color.BLACK);
		String welcome = "Welcome to Pokemon Fling!";
    	String bar = "Solve problems by finding the fraction represented by the blue squares in the health bar below ";
    	String inst = "Just fling the pokeball at the pokemon holding the correct answer to capture it";
    	String con = "The game is over once you have captured 6 Pokemon.";
    	String begin = "Tap the pokeball to begin, good luck and have fun!";
		canvas.drawText(welcome, canvas.getWidth()/2 - welcome.length()*(7),50,  p);
		canvas.drawText(bar, canvas.getWidth()/2 - bar.length()*(7),150,  p);
		canvas.drawBitmap(fraction, canvas.getWidth()/2 - 122,238, null);
		canvas.drawText(inst, canvas.getWidth()/2 - inst.length()*(7),350,  p);
		canvas.drawText(con, canvas.getWidth()/2 - con.length()*(7),450,  p);
		canvas.drawText(begin, canvas.getWidth()/2 - begin.length()*(7),490,  p);
		canvas.drawBitmap(ball, canvas.getWidth()/2 - 25 ,550, null);
	}

}
