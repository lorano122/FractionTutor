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
    	String bar = "Solve problems by finding the fraction represented";
    	String bar2 = "by the blue squares in the health bar below";
    	String inst = "Just fling the pokeball at the pokemon holding the correct answer to capture it";
    	String con = "The game is over once you have captured 6 Pokemon";
    	String begin = "Tap the pokeball to begin, good luck and have fun!";
		canvas.drawText(welcome, canvas.getWidth()/2 - welcome.length()*(7),50,  p);
		canvas.drawText(bar, canvas.getWidth()/2 - bar.length()*(7),150,  p);
		canvas.drawText(bar2, canvas.getWidth()/2 - bar2.length()*(7),190,  p);
		canvas.drawBitmap(fraction, canvas.getWidth()/2 - fraction.getWidth()/2,258, null);
		canvas.drawText(inst, canvas.getWidth()/2 - inst.length()*(7),350,  p);
		canvas.drawText(con, canvas.getWidth()/2 - con.length()*(7)-25,450,  p);
		canvas.drawText(begin, canvas.getWidth()/2 - begin.length()*(7),490,  p);
		canvas.drawBitmap(ball, canvas.getWidth()/2 - 25 ,540, null);
	}
	
	public void createAdditionScreen(Bitmap fraction, Bitmap ball)
	{
		 
		p.setColor(Color.WHITE);
    	canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), p);
    	p.setTextSize(30);
    	p.setColor(Color.BLACK);
		String welcome = "Welcome to Master Addition!";
    	String exp1 = "Create visual representations of fractions to solve the equation";
    	String exp2 = "The equation is displayed in the pokedex";
    	String inst = "Just fill in the circle to create the correct fraction of the master ball";
    	String con = "The game is over when you have hatched 6 eggs";
    	String begin = "Tap the master ball to begin, good luck and have fun!";
    	String explination = "here is an example of 5/6";
		canvas.drawText(welcome, canvas.getWidth()/2 - welcome.length()*(10),50,  p);
		canvas.drawText(exp1, canvas.getWidth()/2 - exp1.length()*(10),150,  p);
		canvas.drawText(exp2, canvas.getWidth()/2 - exp2.length()*(10),190,  p);
		//canvas.drawBitmap(fraction, canvas.getWidth()/2 - fraction.getWidth()/2,258, null);
		canvas.drawText(inst, canvas.getWidth()/2 - inst.length()*(10),250,  p);
		canvas.drawText(con, canvas.getWidth()/2 - con.length()*(10),350,  p);
		canvas.drawText(begin, canvas.getWidth()/2 - begin.length()*(10),390,  p);
		canvas.drawBitmap(fraction, canvas.getWidth()/2 - fraction.getWidth()/2,410, null);
		p.setTextSize(20);
		canvas.drawText(explination, canvas.getWidth()/2 - explination.length()*(10),390,  p);
		canvas.drawBitmap(ball, canvas.getWidth()/2 - 25 ,540, null);
	}

}
