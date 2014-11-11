package ru.vitkt.motionsynthesizer;

import java.io.File;

import com.example.sinwave.R;

import ru.vitkt.soundgenerators.BitmapSinwaveGenerator;
import ru.vitkt.soundgenerators.FileDataPlayer;
import ru.vitkt.soundgenerators.SinAmpMotionGenerator;
import ru.vitkt.soundgenerators.SinwaveGenerator;
import ru.vitkt.soundgenerators.SquarewaveGenerator;


import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;

public class MainActivity extends Activity {
//	SquarewaveGenerator generator;
	//SinAmpMotionGenerator generator;
	BitmapSinwaveGenerator generator;
//	FileDataPlayer generator;
	
	Camera cam;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
						//generator = new SinAmpMotionGenerator(this);
						//generator.setBufferSize(100);
		
		cam = Camera.open(0);
		cam.takePicture(null, new Camera.PictureCallback() {
			
			@Override
			public void onPictureTaken(byte[] data, Camera camera) {
				// TODO Auto-generated method stub
				Bitmap t = BitmapFactory.decodeByteArray(data,0,data.length);
				generator.setBitmap(t);
			}
		}, null);
		
		
		//String sdcardPath = Environment.getExternalStorageDirectory()
			//	.getPath();
//		generator = new FileDataPlayer(new File(sdcardPath, "soundLog.txt"));
		Bitmap pic = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		generator = new BitmapSinwaveGenerator(pic);
		generator.play();
		
		Button btn = new Button(this);
		btn.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
					//				double freq = generator.getFrenquency();
					//				
					//				freq += 100f;
					//				if (freq >1000f)
					//					freq = 100f;
					//				
					//				generator.setFrequency(freq);
				// TODO Auto-generated method stub
				
			}
		});
		setContentView(btn);
	}

	@Override
	protected void onPause() {

		super.onPause();
				//generator.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
			//			generator.onResume();
	}
}