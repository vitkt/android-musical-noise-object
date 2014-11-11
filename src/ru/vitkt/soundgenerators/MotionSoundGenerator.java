package ru.vitkt.soundgenerators;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


public abstract class MotionSoundGenerator extends BufferSoundGenerator
		implements SensorEventListener {
	private Context context;

	private final SensorManager mSensorManager;
	private final Sensor mAccelerometer;
	private final Sensor mOri;


	
	public MotionSoundGenerator(Context _context) {
	
		
		context = _context;

		mSensorManager = (SensorManager) context
				.getSystemService(Context.SENSOR_SERVICE);

		mAccelerometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		mOri = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
	}

	private float oriX = 1.0f;
	private float oriY = 1.0f;
	private float oriZ = 1.0f;

	private float dX = 1.0f;
	private float dY = 1.0f;
	private float dZ = 1.0f;

	protected synchronized float getOriX() {
		return oriX;
	}

	protected synchronized float getOriY() {
		return oriY;
	}

	protected synchronized float getOriZ() {
		return oriZ;
	}

	protected synchronized double getSensorAmp() {
		double amp = getdX()*getdX() + getdY()*getdY() + getdZ()*getdZ();
		amp = Math.sqrt(amp);
		//FileBufferLog.writeDataAmp(amp);
		return amp;
	}

	private synchronized void setOriX(float oriX) {
		this.oriX = oriX;
	}

	private synchronized void setOriY(float oriY) {
		this.oriY = oriY;
	}

	private synchronized void setOriZ(float oriZ) {
		this.oriZ = oriZ;
	}

	protected synchronized float getdX() {
		return dX;
	}

	private synchronized void setdX(float dX) {
		this.dX = dX;
	}

	protected synchronized float getdY() {
		return dY;
	}

	private synchronized void setdY(float dY) {
		this.dY = dY;
	}

	protected synchronized float getdZ() {
		return dZ;
	}

	private synchronized void setdZ(float dZ) {
		this.dZ = dZ;
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		Sensor sensor = event.sensor;

		if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			setdX(event.values[0]);
			setdY(event.values[1]);
			setdZ(event.values[2]);
		}
		if (sensor.getType() == Sensor.TYPE_ORIENTATION) {
			setOriX(event.values[0]);
			setOriY(event.values[1]);
			setOriZ(event.values[2]);
		}

	}

	public void onResume() {
		mSensorManager.registerListener(this, mAccelerometer,
				SensorManager.SENSOR_DELAY_FASTEST);
		mSensorManager.registerListener(this, mOri,
				SensorManager.SENSOR_DELAY_FASTEST);
	}

	public void onPause() {
		mSensorManager.unregisterListener(this);
	}

}
