package com.example.pokemonfractions;

import helpers.Location;
import helpers.TutorialScreenCreator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import mathClasses.PokeFlingMath;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.Toast;

public class PokeFling extends Activity {

	private static final String DEBUG_TAG = "Fling";

    private PokeFlingMath fractions = new PokeFlingMath(10);
    private ArrayList<Location> icons = new ArrayList<Location>();
    private ArrayList<String> wrongAns = fractions.randomAnswers(3);
    private int num = fractions.getAnswer();
    private String answer = num +" / 10";
    private int question = 0,correct = 0;
    private int set = fractions.getRandom();
    private HashMap<Integer,String> matched = new HashMap<Integer,String>();
    private boolean done = false;
    private boolean canMove = true;
    private boolean moveOn = false;
    private boolean initializing = true;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_poke_fling);
	    FrameLayout frame = (FrameLayout) findViewById(R.id.graphics_holder);
	    PlayAreaView image = new PlayAreaView(this);
	    this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	    frame.addView(image);

        
	}
	

	 protected void onResume()
     {
     	super.onResume();     	
     }

	 protected void onStop()
	 {
		 super.onStop();
	 }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_poke_fling, menu);
        return true;
    }
    
 
    private class PlayAreaView extends View {

        private GestureDetector gestures;
        private Matrix translate;
        private Bitmap ball;
//        private Bitmap icon;

        private Matrix animateStart;
        private OvershootInterpolator animateInterpolator;
        private long startTime;
        private long endTime;
        private float totalAnimDx;
        private float totalAnimDy;
        private float cx=573,cy=275;
        private float[] centerArray = { 1, 0, cx, 0, 1, cy, 0, 0, 1 };
        private int[] fPics = {R.drawable.f0,R.drawable.f1,R.drawable.f2,R.drawable.f3,R.drawable.f4,R.drawable.f5,R.drawable.f6,
        		R.drawable.f7,R.drawable.f8,R.drawable.f9,R.drawable.f10};
        private Paint p = new Paint();
        private String message = "";
        
       public PlayAreaView(Context context) {
            super(context);
            translate = new Matrix();
            translate.setValues(centerArray);
            gestures = new GestureDetector(PokeFling.this, new GestureListener(this));
            ball = BitmapFactory.decodeResource(getResources(), R.drawable.pokeball);
            p.setTextSize(40);
            p.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            int[] icon = {R.drawable.squirtle,R.drawable.gyrados,R.drawable.snorlax,R.drawable.onix};
        	for(int i = 0; i < 4; i++)
        	{
        		Bitmap b = BitmapFactory.decodeResource(getResources(), icon[i]);
        		Location l = new Location(b,i+1);
        		Log.d(DEBUG_TAG, "creating locations");
        		icons.add(l);
        	}       
            
        }

        public void onAnimateMove(float dx, float dy, long duration) {
            animateStart = new Matrix(translate);
            animateInterpolator = new OvershootInterpolator();
            startTime = System.currentTimeMillis();
            endTime = startTime + duration;
            totalAnimDx = dx;
            totalAnimDy = dy;
            post(new Runnable() {
                public void run() {
                    onAnimateStep();
                }
            });
        }

        private void onAnimateStep() {
            long curTime = System.currentTimeMillis();
            float percentTime = (float) (curTime - startTime) / (float) (endTime - startTime);
            float percentDistance = animateInterpolator.getInterpolation(percentTime);
            float curDx = percentDistance * totalAnimDx;
            float curDy = percentDistance * totalAnimDy;
            translate.set(animateStart);
            onMove(curDx, curDy);
            
            //Log.v(DEBUG_TAG, "We're " + percentDistance + " of the way there!");
            
            if (percentTime < 1.0f) 
            {
                post(new Runnable() {
                    public void run() {
                        onAnimateStep();
                    }
                });
            }
            
        }

        public void onMove(float dx, float dy) {        	
            translate.postTranslate(dx, dy);
            invalidate();
        }

        public void onResetLocation() {
            translate.setValues(centerArray);
            invalidate();
        }
        
        public void onResetLocation(boolean flag) {
            if(flag)
            {
            	translate.reset();
            	invalidate();
            }
        }

        public void onSetLocation(float dx, float dy) {
            translate.postTranslate(dx, dy);
            invalidate();
        }

        public void stayOnTarget(float dx, float dy)
        {
        	canMove = false;
		    onResetLocation(true);
		    onMove(dx,dy);
        	final Handler h1 = new Handler();
        	h1.postDelayed(new Runnable() 
			{
				public void run() 
				{
					Log.d(DEBUG_TAG, "on target");
					onResetLocation();
					canMove = true;
				}
			}, 500);        	
        }
        
        public void wiggle()
        {			
        	canMove = false;
 			final float shift = ball.getWidth()/4;
 			onMove(-shift,0);
			final Handler backHandler = new Handler();
			backHandler.postDelayed(new Runnable() {
             public void run() {
             	onResetLocation();
             	onMove(-shift*2,0);
   	          backHandler.postDelayed(new Runnable() {
 	                public void run() {
 	                	onResetLocation();
 	                	onMove(-shift,0); 
 	                	canMove = true;
 	                }
 	            	}, 150);  	       
             }             
         }, 150);
			onResetLocation();
			
         
        }

        public void drawAnswers(Canvas canvas)
        {
        	Bitmap b = BitmapFactory.decodeResource(getResources(),R.drawable.scoreball);
        	float x = 900;
        	float y = 550;
        	float size = 55;
        	for(int i = 1; i <= 6; i++)
        	{
        		if(i <= correct)
        		{
        			canvas.drawBitmap(b, x, y,p);
        			x+=size;
        		}
        	}
        }
        
    	public void setTexts(Canvas canvas)
    	{
            p.setTextSize(40);
            p.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
    		int i = 0;
    		p.setColor(Color.BLACK);
    		for(Location l : icons)
    		{
    			float x = l.getX()-25;
    			float y = l.getY()-30;
    			String temp;
    			if(set == l.getIndex())
    			{
    				canvas.drawText(answer,x , y, p);
    				temp = answer;
    			}
    			else
    			{
    				canvas.drawText(wrongAns.get(i),x , y, p);    				
    				temp = wrongAns.get(i);
    				i++;
    			}
    			matched.put(l.getIndex(), temp);
    		}
    		if(question == 0)
    			canvas.drawText("Welcome", 50, 550, p);
    		else if(question > 0 && correct !=6)
    		{
                p.setTextSize(30);
    			canvas.drawText("Your previous answer was", 50, 500, p);
    			canvas.drawText(message, 50, 550, p);
    		}
    		else if(correct == 6)
    		{
    			 p.setTextSize(30);
     			canvas.drawText("Finished", 50, 500, p);
     			canvas.drawText(message, 50, 350, p);
    		}
    	}

    	public void nextQuestion(String ans)
    	{
    		ans = ans.replaceAll(" / ", "X");
    		String[] s = ans.split("X");    		
    		int a = Integer.parseInt(s[0]);
    		if(a == num)
    		{
    			correct++;
    			//makeToast("correct");
    			message = "correct";
    		}
    		else
    		{
    			//makeToast("incorrect");
    			message = "incorrect";
    		}
    		question++;
    		
			fractions.randFract();
			set = fractions.getRandom();
			num = fractions.getAnswer();
			wrongAns.clear();
			wrongAns = fractions.randomAnswers(3);
			answer = num +" / 10";   
			
			if(correct == 6 && !done)
			{
				//makeToast("done");
				done = true;
            	moveOn = true;
    			message = "You have answered 6 questions correctly and you are now ready for the next stage";

            }
			canMove = true;
    	}
      

    	
    	public void updateFraction(Canvas canvas)
    	{
            
            Bitmap b = BitmapFactory.decodeResource(getResources(), fPics[num]);
            canvas.drawBitmap(b, 10, 10, null);
    	}
        @Override
        protected void onDraw(Canvas canvas) {          
            Matrix m = canvas.getMatrix();      
            cx = canvas.getWidth()/2 - ball.getWidth()/2;
            cy = canvas.getHeight()/2 - ball.getHeight();
            p.setColor(Color.rgb(174,208,198));
            canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), p);
            for(Location l : icons)
            {
            	l.setLocation(canvas.getWidth(), canvas.getHeight());
            	l.draw(canvas);            	
            }
            updateFraction(canvas);
            drawAnswers(canvas);
            setTexts(canvas);
            canvas.drawBitmap(ball, translate, null);
        	if(initializing)
        	{
                centerArray[2] = cx;
                centerArray[5] = cy;
		    	start(canvas);		    	
		    	
        	}
            if(done && correct == 6)
            {
            	
     			moveOn(canvas);
            }
  
        }
        
        public void start(Canvas canvas)
        {
        	Bitmap fraction = BitmapFactory.decodeResource(getResources(), R.drawable.f6); 
        	TutorialScreenCreator t = new TutorialScreenCreator(p,canvas);
        	t.createFlingScreen(fraction,ball);		
        }
        
        public void moveOn(Canvas canvas)
        {
        	final Intent intent = new Intent(PokeFling.this.getBaseContext(), FlingScore.class);
        	intent.putExtra("results", " "+question+" ");
			final Handler h1 = new Handler();
			if(moveOn && done)
			{
				moveOn=false;
			  	h1.postDelayed(new Runnable() 
				{
					public void run() 
					{
						Log.d(DEBUG_TAG, "moving on");

						recreate();
						startActivity(intent);
						
					}
				}, 4000);
			}
        }
			
        @Override
        public boolean onTouchEvent(MotionEvent event) {
        	if(moveOn)
        		return false;
        	else
        		return gestures.onTouchEvent(event);
        }
        
        private class GestureListener implements GestureDetector.OnGestureListener,GestureDetector.OnDoubleTapListener {
        	PlayAreaView view;
        	
        	public GestureListener(PlayAreaView view)
        	{
        		this.view = view;
        		view.setLongClickable(true);
        	}
        	
        	
        	public boolean onDown(MotionEvent e) {        
        		Log.v( DEBUG_TAG, "onDown" );
        		float x = e.getRawX() - view.ball.getWidth()/2;
    			float y = e.getRawY() - view.ball.getHeight()*2;
     			float tolerance = 40;
     			boolean w1 = Math.abs((x - cx)) <= tolerance;
     			boolean w2 = Math.abs((y/2 - cy)) <= tolerance;
     			Log.v( DEBUG_TAG, "x: " +x+" y: "+y + " cx: "+cx+ " cy: "+cy);
     			Log.v( DEBUG_TAG, "touch y: " + e.getRawY() );  
     			Log.v( DEBUG_TAG, "view y: " +  view.getBottom()); 
     			Log.v( DEBUG_TAG, "w1: " +w1+" w2: " + w2 );
     			if(initializing)
     			{
     				view.onResetLocation();
     				initializing = false;
     			}
     			else if(!(w1 || w2) && canMove)
     			{
     				Log.v( DEBUG_TAG, "onDown" );
     				view.wiggle(); 
     				canMove = true;
     				
     			}  
     			else if(done || !canMove)
     			{
     				Log.v( DEBUG_TAG, "waiting" );
     			}
     			
     			return true;
        	}

        	public boolean onDoubleTap(MotionEvent e) {
        	    Log.v(DEBUG_TAG, "onDoubleTap");
        	    view.onResetLocation();
        	    return true;
        	}

    		public boolean onDoubleTapEvent(MotionEvent e) {
    			// TODO Auto-generated method stub
    			return false;
    		}

    		public boolean onSingleTapConfirmed(MotionEvent e) {
    			return false;
    		}


    		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
    			Log.v(DEBUG_TAG, "fling");
    		    float dx=0,dy=0;
    		    String ans="11/10";
    		    if(fractions.isBigger(velocityX, velocityY))
    		    {
    		    	
	    		    if(velocityX < 0)
	    		    {
	    		    	dx = icons.get(0).getX();
	    		    	dy = icons.get(0).getY();
	    		    	ans = matched.get(icons.get(0).getIndex());
	    		    	
	    		    }
	    		    if(velocityX > 0)
	    		    {
	    		    	dx = icons.get(3).getX();
	    		    	dy = icons.get(3).getY();
	    		    	ans = matched.get(icons.get(3).getIndex());
	    		    	
	    		    }
	    		  
    		    }
	    		else
	    		{
    			  if(velocityY > 0)
	    		    {
    				  	dx = (float) (icons.get(2).getX());
	    		    	dy = icons.get(2).getY();
	    		    	ans = matched.get(icons.get(2).getIndex());
	    		    	
	    		    }
	    		    if(velocityY < 0)
	    		    {
	    		    	
	    		    	dx = (float) (icons.get(1).getX());
	    		    	dy = icons.get(1).getY();
	    		    	ans = matched.get(icons.get(1).getIndex());
	    		    }

	    		}
    		    view.stayOnTarget(dx, dy);
    		    nextQuestion(ans);
    		    
    		    return true;
    		}


    		public void onLongPress(MotionEvent e) {
    			// TODO Auto-generated method stub
    			
    		}

    		public boolean onScroll(MotionEvent e1, MotionEvent e2,
    		    float distanceX, float distanceY) {
//    		    Log.v(DEBUG_TAG, "onScroll");
    		    //view.onMove(-distanceX, -distanceY);
    		    return true;
    		}

    		public void onShowPress(MotionEvent e) {
    			// TODO Auto-generated method stub
    			
    		}

    		public boolean onSingleTapUp(MotionEvent e) {

    			return false;
    		}

    }


    }


}
