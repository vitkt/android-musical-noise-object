package ru.vitkt.soundgenerators.plugins;

import ru.vitkt.soundgenerators.ISoundProcessor;

public class Gain implements ISoundProcessor{
	
	private float gainValue = 1.0f;
	@Override
	public void processBuffer(short[] buffer, int offset, int bufferSize) {
		for(int i=offset;i<offset+bufferSize;i++)
		{
			buffer[i] = (short)(buffer[i]*gainValue);
		}
		
	}
	public float getGainValue() {
		return gainValue;
	}
	public void setGainValue(float gainValue) {
		this.gainValue = gainValue;
	}

}
