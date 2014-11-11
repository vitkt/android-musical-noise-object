package ru.vitkt.soundgenerators;

public class SquarewaveGenerator extends BufferSoundGenerator {

	@Override
	public void processBuffer(short[] buffer, int offset, int bufferSize) {
		int amp = 7000;
		int mul = 1;
		for (int i = 0; i < bufferSize; i++) {
			buffer[i] = (short) (amp*mul);
			if ((i%50)==0)
				mul*=-1;
		}

	}

}
