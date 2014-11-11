package ru.vitkt.soundgenerators;

import android.graphics.Bitmap;
import android.graphics.Color;

public class BitmapSinwaveGenerator extends SinwaveGenerator {
	Bitmap b;
	Object lockObj = new Object();

	public BitmapSinwaveGenerator(Bitmap _b) {
		super();
		b = _b;
	}

	public void setBitmap(Bitmap _b) {
		
		synchronized (lockObj) {
			b.recycle();
			b = _b;
		}
	}

	@Override
	public void processBuffer(short[] buffer, int offset, int bufferSize) {

		super.processBuffer(buffer, offset, bufferSize);
		synchronized (lockObj) {

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
	}
}
