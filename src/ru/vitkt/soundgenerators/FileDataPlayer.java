package ru.vitkt.soundgenerators;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class FileDataPlayer extends BufferSoundGenerator {

	short[] data;

	public FileDataPlayer(File file) {
		super();
		loadFile(file);
		setBufferSize(50000);
	}

	private void loadFile(File file) {
		try {
			FileReader reader = new FileReader(file);
			char[] buf = new char[10000];
			StringBuilder builder = new StringBuilder();
			while (reader.read(buf) != -1) {
				builder.append(buf);
			}
		
			String[] stringData = builder.toString().split(" ");
			data = new short[stringData.length];
			for (int i = 0; i < stringData.length; i++) {
				data[i] = Short.valueOf(stringData[i]);
			}
			reader.close();
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	int offsetCounter = 0;

	@Override
	public void processBuffer(short[] buffer, int offset, int bufferSize) {
	
		for (int i = 0; i < bufferSize; i++) {
			
			buffer[i] = data[offsetCounter++];
			if (offsetCounter >= data.length)
				offsetCounter=0;
		}
	}

}
