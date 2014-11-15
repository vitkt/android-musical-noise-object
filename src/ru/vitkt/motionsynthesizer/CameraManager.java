package ru.vitkt.motionsynthesizer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import ru.vitkt.soundgenerators.BitmapSinwaveGenerator;

public class CameraManager {

	BitmapSinwaveGenerator _generator;
	Camera cam;
	Handler cameraHandler;
	Thread handlerThread;
	
	private void initCam() {
		Camera.CameraInfo info = new Camera.CameraInfo();
		for (int i = 0; i < Camera.getNumberOfCameras(); i++) {
			Camera.getCameraInfo(i, info);
			if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
				cam = Camera.open(i);
				// try {
				// cam.setPreviewDisplay(null);
				// } catch (IOException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				cam.startPreview();
				cam.setPreviewCallback(new Camera.PreviewCallback() {

					@Override
					public void onPreviewFrame(byte[] data, Camera camera) {
						Log.i("motion", "preview frame");
						if (_generator != null) {
							Camera.Parameters parameters = camera
									.getParameters();
							int width = parameters.getPreviewSize().width;
							int height = parameters.getPreviewSize().height;

							YuvImage yuv = new YuvImage(data, parameters
									.getPreviewFormat(), width, height, null);

							ByteArrayOutputStream out = new ByteArrayOutputStream();
							yuv.compressToJpeg(new Rect(0, 0, width, height),
									50, out);

							byte[] bytes = out.toByteArray();
							Bitmap t = BitmapFactory.decodeByteArray( bytes, 0,
									bytes.length);
							if (t == null)
								Log.i("motion", "null bitmap(((");
							else
								_generator.setBitmap(t);
						}

					}
				});
				Log.i("motion", "camera number = " + i);
				// takePicture();
				break;
			}
		}
	}

	private void takePicture() {
		Log.i("motion", "takePicture");
		if (cam != null) {
			Log.i("motion", "send take picture");
			System.gc();
			cam.takePicture(null, new Camera.PictureCallback() {

				@Override
				public void onPictureTaken(byte[] data, Camera camera) {
					Log.i("motion", "picture callback");
					if (_generator != null) {
						Bitmap t = BitmapFactory.decodeByteArray(data, 0,
								data.length);
						_generator.setBitmap(t);
						cameraHandler.sendMessage(cameraHandler
								.obtainMessage(1));
					} else
						cameraHandler.sendMessageDelayed(
								cameraHandler.obtainMessage(1), 500);

				}
			}, null);
		} else
			Log.i("motion", "can not take");

	}

	private void uninitCam() {
		cam.stopPreview();
		cam.release();
		cam = null;

	}

	CountDownLatch cl = new CountDownLatch(1);

	CameraManager() {
		Log.i("motion", "thread init");
		handlerThread = new Thread(new Runnable() {

			@Override
			public void run() {
				Looper.prepare();
				cameraHandler = new Handler(new Handler.Callback() {

					@Override
					public boolean handleMessage(Message msg) {
						Log.i("motion", "getMessage");
						switch (msg.what) {
						case 0:
							Log.i("motion", "init");
							initCam();
							break;
						case 1:
							Log.i("motion", "takepic");
							takePicture();
							break;
						case 2:
							Log.i("motion", "uninit");
							uninitCam();
							break;
						}
						return false;
					}

				});
				cl.countDown();
				Looper.loop();

			}
		});
		handlerThread.start();
	}

	public void init() {
		try {
			cl.await();

			cameraHandler.sendMessage(cameraHandler.obtainMessage(0));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void uninit() {
		try {
			cl.await();
			cameraHandler.sendMessage(cameraHandler.obtainMessage(2));
		} catch (InterruptedException e) {

		}
	}

	public void startCapturing(BitmapSinwaveGenerator generator) {
		_generator = generator;
	}
}
