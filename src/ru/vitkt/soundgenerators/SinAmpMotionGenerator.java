package ru.vitkt.soundgenerators;

import android.content.Context;
import android.util.Log;

public class SinAmpMotionGenerator extends MotionSoundGenerator {
	double twopi = 8. * Math.atan(1.);
	double _frequency = 440.f;
	double ph = 0.0;
	double amp = 2000;

	public SinAmpMotionGenerator(Context _context) {
		super(_context);

	}

	double oldVal = 10.f;

	public void setFrequency(double frequency) {
		_frequency = frequency;
	}

	public double getFrenquency() {
		return _frequency;
	}

	public void filterBuffer(short[] buffer, int offset, int bufferSize,float a){
		// y[i-1] + α * (x[i] - y[i-1])
		short oldVal = buffer[0];
		//float a = 0.2f;
		for(int i=1;i<bufferSize;i++)
		{
			oldVal = buffer[i-1];
			short temp = (short) (oldVal + a*(buffer[i]-oldVal));
			//oldVal = buffer[i];
			buffer[i]= temp;
		}
	}
	
	@Override
	public void processBuffer(short[] buffer, int offset, int bufferSize) {

		float maxVolConst = 20f;
		double currentVal = getSensorAmp();
		float vol = ((float) currentVal - 10f) / maxVolConst;
		oldVal = currentVal;

		Log.i("musicgen", "vol " + vol);
		if (vol > 1.0f)
			vol = 1.0f;
		//_track.setStereoVolume(vol, vol);

		double currentAmp = amp;
		for (int i = 0; i < bufferSize; i++) {

			buffer[i] = (short) (currentAmp * Math.sin(ph));
			// FileBufferLog.writeDataSound(buffer[i]);
			ph += twopi * _frequency / BufferSoundGenerator.sampleRate;
		}
		
		filterBuffer(buffer, offset, bufferSize,vol);

	}

}
