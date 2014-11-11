package ru.vitkt.soundgenerators;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.os.Environment;

public class FileBufferLog {
	static FileWriter writerSound;
	static FileWriter writerAmp;

	static void writeDataSound(short data) {
		try {
			if (writerSound == null) {
				String sdcardPath = Environment.getExternalStorageDirectory()
						.getPath();
				writerSound = new FileWriter(new File(sdcardPath, "soundLog.txt"),
						true);

			}

			writerSound.append(Short.toString(data) + " ");
			writerSound.flush();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	static void writeDataAmp(double data) {
		try {
			if (writerAmp == null) {
				String sdcardPath = Environment.getExternalStorageDirectory()
						.getPath();
				writerAmp = new FileWriter(new File(sdcardPath, "ampLog.txt"),
						true);

			}

			writerAmp.append(Double.toString(data) + " ");
			writerAmp.flush();

		} catch (IOException e) {
			
			e.printStackTrace();
		}

	}

}
