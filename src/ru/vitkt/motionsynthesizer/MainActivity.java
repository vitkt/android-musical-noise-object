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
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;

public class MainActivity extends Activity {
//	SquarewaveGenerator generator;
	//SinAmpMotionGenerator generator;
	BitmapSinwaveGenerator generator;
//	FileDataPlayer generator;
	
	CameraManager cm;
	static final int REQUEST_IMAGE_CAPTURE = 1;
	ImageView pict1;
	void updatePicture()
	{
		 Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
		        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
		    }
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
	        Bundle extras = data.getExtras();
	        Bitmap imageBitmap = (Bitmap) extras.get("data");
	        generator.setBitmap(imageBitmap);
	    }
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
						//generator = new SinAmpMotionGenerator(this);
						//generator.setBufferSize(100);
		
		
	
		cm = new CameraManager();
		
		//String sdcardPath = Environment.getExternalStorageDirectory()
			//	.getPath();
//		generator = new FileDataPlayer(new File(sdcardPath, "soundLog.txt"));
		Bitmap pic  = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		generator = new BitmapSinwaveGenerator(pic,  new BitmapSinwaveGenerator.uiCallback() {
			
			@Override
			public void updateUi(double light) {
				final float t = (float)light;
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							pict1.setAlpha(t);
						}
					});
				
			}
		});
		
		
		Button btn = new Button(this);
		btn.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//updatePicture();
				
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
		//setContentView(btn);
		
		setContentView(R.layout.main_layout);
		pict1 = (ImageView)findViewById(R.id.psywall);
		
		
	}

	@Override
	protected void onPause() {
		generator.stop();
		super.onPause();
		//cm.uninit();
				//generator.onPause();
	}
	
	@Override
		protected void onStop() {
		 cm.uninit();
			super.onStop();
		}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		generator.play();
		super.onResume();
		cm.startCapturing(generator); //?????
		cm.init();
		
		//initCam();
//		cam.startPreview();

			//			generator.onResume();
	}
}