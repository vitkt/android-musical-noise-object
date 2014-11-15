package ru.vitkt.soundgenerators;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public abstract class BufferSoundGenerator implements ISoundGenerator, ISoundProcessor {
	private Thread _musicThread;
	protected AudioTrack _track;
	protected int _bufferSize;
	protected short samples[];

	protected final static int sampleRate = 44100;
	protected final static int defaultBufferSize = 590;

	private boolean isRunning = false;

	private static int getDefaultBufferSize() {
		// return AudioTrack.getMinBufferSize(sampleRate,
		// AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
		return defaultBufferSize;
	}

	public BufferSoundGenerator(int bufferSize) {
		_bufferSize = bufferSize;
	}

	public BufferSoundGenerator() {
		_bufferSize = getDefaultBufferSize();
	}
	public void setBufferSize(int bufferSize) {
		_bufferSize = bufferSize;
	}
	private void InitGenerator() {
		_musicThread = new Thread() {
			public void run() {

				setPriority(Thread.MAX_PRIORITY);
				_track = new AudioTrack(AudioManager.STREAM_MUSIC, sampleRate,
						AudioFormat.CHANNEL_OUT_STEREO,
						AudioFormat.ENCODING_PCM_16BIT, _bufferSize*2,
						AudioTrack.MODE_STREAM);

				samples = new short[_bufferSize];
				_track.play();

				isRunning = true;

				// synthesis loop
				while (isRunning) {
					processBuffer(samples, 0, _bufferSize);
					_track.write(samples, 0, _bufferSize);
				}
				_track.stop();
				_track.release();
			}
		};
	}

	@Override
	public void play() {
		InitGenerator();
		_musicThread.start();

	}

	@Override
	public void stop() {
		isRunning = false;
		try {
			_musicThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
