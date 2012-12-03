package helpers;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class MasterLocation {
	
	private float x,y;
	private Bitmap icon;
	private int index;
	
	public MasterLocation(Bitmap icon, float x, float y)
	{
		this.icon = icon;
		this.x = x;
		this.y = y;
	}
	
	
	public void draw(Canvas canvas, Paint p)
	{
		canvas.drawBitmap(icon, x, y, p);
	}
	
	public void setIcon(Bitmap b)
	{
		icon = b;
	}

}
