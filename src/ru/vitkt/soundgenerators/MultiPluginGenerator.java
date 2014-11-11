package ru.vitkt.soundgenerators;

import java.util.ArrayList;

public abstract class MultiPluginGenerator extends BufferSoundGenerator {
	public MultiPluginGenerator() {
		super();
		initPlugins();
	}

	protected ArrayList<ISoundProcessor> plugins = new ArrayList<ISoundProcessor>();

	protected abstract void initPlugins();

	@Override
	public void processBuffer(short[] buffer, int offset, int bufferSize) {
		for (ISoundProcessor plugin : plugins) {
			plugin.processBuffer(buffer, offset, bufferSize);
		}
	}

}
