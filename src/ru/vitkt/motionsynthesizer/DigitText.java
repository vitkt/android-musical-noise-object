package ru.vitkt.motionsynthesizer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

public class DigitText extends TextView {

	public DigitText(Context context) {
		super(context);
		setBackgroundColor(Color.BLACK);
		setTextColor(Color.GREEN);
		setTextSize(48);
		mDetector = new GestureDetector(context, gListener);
		
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		mDetector.onTouchEvent(event);
		
		return super.onTouchEvent(event);
	}
	GestureDetector mDetector;
	GestureDetector.OnGestureListener gListener = new GestureDetector.OnGestureListener() {
		
		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public void onShowPress(MotionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
				float distanceY) {
			if (e2.getY()-e1.getY() > 20 )
				setValue(value+10);
			else if (e2.getY()-e1.getY() < -20 )
				setValue(value-10);
			return false;
		}
		
		@Override
		public void onLongPress(MotionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean onDown(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}
	};
	int getValue() {
		return value;
	}

	void setValue(int value) {
		this.value = value;
		setText(Integer.toString(value));
	}

	private int value;

}
