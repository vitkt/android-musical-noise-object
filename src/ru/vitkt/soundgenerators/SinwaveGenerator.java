package ru.vitkt.soundgenerators;

public class SinwaveGenerator extends BufferSoundGenerator {
	 double twopi = 8.*Math.atan(1.);
     double fr = 440.f;
     double ph = 0.0;
     double amp = 7000;
	public SinwaveGenerator() {
		super();
		
	}
	@Override
	public void processBuffer(short[] buffer, int offset, int bufferSize) {
        for(int i=0; i < bufferSize; i++){
            
            buffer[i] = (short) (amp *Math.sin(ph));
            ph += twopi*fr/BufferSoundGenerator.sampleRate;
          }

	}

}
