package ru.vitkt.soundgenerators;

public interface ISoundProcessor {
	 abstract void processBuffer(short[] buffer, int offset, int bufferSize);
}
