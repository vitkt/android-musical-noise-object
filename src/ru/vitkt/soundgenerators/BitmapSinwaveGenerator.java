package ru.vitkt.soundgenerators;

import android.graphics.Bitmap;
import android.graphics.Color;

public class BitmapSinwaveGenerator extends SinwaveGenerator {
	Bitmap b = null;
	Object lockObj = new Object();
	uiCallback cb;
	public BitmapSinwaveGenerator(Bitmap _b, uiCallback _cb) {
		super();
		b = _b;
		cb = _cb;
	}

	public void setBitmap(Bitmap _b) {

		synchronized (lockObj) {
			if (b!=null)
				b.recycle();
			b = _b;
		}
	}
	public interface uiCallback
	{
		void updateUi(double light);
	}
	public void processBufferLight(short[] buffer, int offset, int bufferSize)
	{
		for (int i = 0; i < bufferSize; i++) {
			int color = b.getPixel(i % b.getWidth(), i % b.getHeight());
			float r = Color.red(color);
			float g = Color.green(color);
			float b = Color.blue(color);
			double h = Math.sqrt(r * r + g * g + b * b);
			double maxH = Math.sqrt(255 * 255 * 3);
			buffer[i] *= (h / maxH);
		}
	}
	
	public void processBufferColor(short[] buffer, int offset, int bufferSize)
	{
		
		for (int i = 0; i < bufferSize; i++) {
			int color = b.getPixel(i % b.getWidth(), i % b.getHeight());
			float r = Color.red(color);
			float g = Color.green(color);
			float b = Color.blue(color);
			
			double h = Math.sqrt(r * r + g * g + b * b);
			fr=300f+(200f*(r/255f));
			double maxH = Math.sqrt(255 * 255 * 3);
			buffer[i] *= (h / maxH);
			
		}
		int color = b.getPixel(100 % b.getWidth(), 100 % b.getHeight());
		float r = Color.red(color);
		float g = Color.green(color);
		float b = Color.blue(color);
		
		double h = Math.sqrt(r * r + g * g + b * b);
		fr=300f+(200f*(r/255f));
		double maxH = Math.sqrt(255 * 255 * 3);
		cb.updateUi(h / maxH);
	}
	
	@Override
	public void processBuffer(short[] buffer, int offset, int bufferSize) {

		super.processBuffer(buffer, offset, bufferSize);
		synchronized (lockObj) {
				processBufferColor(buffer, offset, bufferSize);
		
			
		}
	}
}
