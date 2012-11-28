package com.example.pokemonfractions;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.ActivityInfo;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;

public class Tutorial extends Activity {
	
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
        setContentView(R.layout.activity_tutorial);
        Button resetButton = ( Button )findViewById( R.id.reset );
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
        resetButton.setOnClickListener( startOver );
        Button battleButton = ( Button )findViewById( R.id.start );
        battleButton.setOnClickListener( battle );
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
	
	View.OnClickListener battle = new View.OnClickListener() {
		
		public void onClick( View v ) {
			Intent i = new Intent( Tutorial.this, PokeFling.class );
			startActivity( i );
		}

		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			
		}
	};
	
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
        private float[] centerArray = { 1, 0, 360, 0, 1, 520, 0, 0, 1 };
        
        public PlayAreaView(Context context) {
            super(context);
            translate = new Matrix();
            translate.setValues(centerArray);
            gestures = new GestureDetector(Tutorial.this, new GestureListener(this));
            ball = BitmapFactory.decodeResource(getResources(), R.drawable.pokeball);
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

    public void onSetLocation(float dx, float dy) {
        translate.postTranslate(dx, dy);
    }
     

  
    @Override
    protected void onDraw(Canvas canvas) {
        // Log.v(DEBUG_TAG, "onDraw");
    	

    	canvas.drawBitmap(ball, translate, null);
        
        Matrix m = canvas.getMatrix();


//        Log.d(DEBUG_TAG, "Matrix: " + translate.toShortString());
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
