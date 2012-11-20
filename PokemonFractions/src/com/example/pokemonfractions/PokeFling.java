package com.example.pokemonfractions;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;

public class PokeFling extends Activity {

	private static final String DEBUG_TAG = "Fling";
    private Matrix animateStart;
    private OvershootInterpolator animateInterpolator;
    private long startTime;
    private long endTime;
    private float totalAnimDx;
    private float totalAnimDy;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_poke_fling);
	    FrameLayout frame = (FrameLayout) findViewById(R.id.graphics_holder);
	    PlayAreaView image = new PlayAreaView(this);
	    frame.addView(image);
	}
	
	 protected void onResume()
     {
     	super.onResume();
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
        private Bitmap icon;

        private Matrix animateStart;
        private OvershootInterpolator animateInterpolator;
        private long startTime;
        private long endTime;
        private float totalAnimDx;
        private float totalAnimDy;

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
            translate.reset();
            invalidate();
        }

        public void onSetLocation(float dx, float dy) {
            translate.postTranslate(dx, dy);
        }
        
        

        public PlayAreaView(Context context) {
            super(context);
            translate = new Matrix();
            gestures = new GestureDetector(PokeFling.this,
                    new GestureListener(this));
        }

      
        @Override
        protected void onDraw(Canvas canvas) {
            // Log.v(DEBUG_TAG, "onDraw");
        	ball = BitmapFactory.decodeResource(getResources(), R.drawable.pokeball);
//        	icon = BitmapFactory.decodeResource(getResources(), R.drawable.squirtle);
        	
            //canvas.drawBitmap(ball, 360, 520, null);
//            canvas.drawBitmap(icon, 100, 100, null);
           
//            createLocations(icons,canvas);
            //Log.d(DEBUG_TAG, "Matrix: " + translate.toShortString());
            //Log.d(DEBUG_TAG, "Canvas: " + m.toShortString());
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            return gestures.onTouchEvent(event);
        }
        
        private class GestureListener implements GestureDetector.OnGestureListener,GestureDetector.OnDoubleTapListener {
        	PlayAreaView view;
        	
        	public GestureListener(PlayAreaView view)
        	{
        		this.view = view;
        	}
        	
        	
        	public boolean onDown(MotionEvent e) {
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
    			Log.v( DEBUG_TAG, "singleTap" ); 
    			final float dx = getX();
    			final float dy = getY();
    			view.onMove( -dx, -dy );
    			return true;
    			//return false;
    		}


    		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
    		    Log.v(DEBUG_TAG, "onFling");
    		    final float distanceTimeFactor = 0.4f;
    		    final float totalDx = (distanceTimeFactor * velocityX/2);
    		    final float totalDy = (distanceTimeFactor * velocityY/2);
    		    view.onAnimateMove(totalDx, totalDy,(long) (1000 * distanceTimeFactor));
    		    return true;
    		}


    		public void onLongPress(MotionEvent e) {
    			// TODO Auto-generated method stub
    			
    		}

    		public boolean onScroll(MotionEvent e1, MotionEvent e2,
    		    float distanceX, float distanceY) {
    		    Log.v(DEBUG_TAG, "onScroll");
    		    view.onMove(-distanceX, -distanceY);
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
