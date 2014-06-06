package org.jarvisbotAndroidClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.jarvisbotAndroidClient.AsyncTask.AsyncAvancer;
import org.jarvisbotAndroidClient.AsyncTask.AsyncDroite;
import org.jarvisbotAndroidClient.AsyncTask.AsyncGauche;
import org.jarvisbotAndroidClient.AsyncTask.AsyncPrendrePhoto;
import org.jarvisbotAndroidClient.AsyncTask.AsyncReculer;
import org.jarvisbotAndroidClient.AsyncTask.AsyncStop;
import org.jarvisbotAndroidClient.AsyncTask.ThreadMap;
import org.jarvisbotLibrary.android.ComAndroid;
import org.jarvisbotLibrary.android.IComAndroid;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.SeekBar;

public class ControlManuel extends Activity{
	//Attributs
	private Button avance;
	private Button recule;
	private Button gauche;
	private Button droite;
	private Button photo;
	private SeekBar vitesse;
	private OSMAndroid OSM;

	private Button buttonVisio;

	private String url;
	private IComAndroid com;

	private LinkedList<Integer> list;
	private Thread threadMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		//ContentView
		setContentView(R.layout.controle_manuel);

		//Initialisation des attributs
		avance = (Button)findViewById(R.id.buttonAvance);
		recule = (Button)findViewById(R.id.buttonRecule);
		gauche = (Button)findViewById(R.id.buttonGauche);
		droite = (Button)findViewById(R.id.buttonDroite);
		photo = (Button)findViewById(R.id.buttonPhoto);
		vitesse = (SeekBar)findViewById(R.id.barVitesse);
		OSM = (OSMAndroid)findViewById(R.id.OSM);

		this.buttonVisio = (Button)findViewById(R.id.buttonVisio);

		//url = "http://"+IP.ip+":8080/KerCarCommunication/";
		url = "http://"+IP.ip+":8080/jarvisbotRaspberryServer/";

		com = ComAndroid.getManager();
		com.setURL(url);

		list = new LinkedList<Integer>();
		list.add(0);
		list.add(1);
		list.add(2);

		threadMap = new ThreadMap(OSM, com);
		threadMap.start();

		buttonVisio.setOnClickListener(new OnClickListener() {

			private boolean appInstalledOrNot(String uri) {
				PackageManager pm = getPackageManager();
				List<PackageInfo> lis = pm.getInstalledPackages(0);
				for(int j = 0; j < lis.size(); j++){
					Log.v("jarvisbotvisioappliclient",lis.get(j).packageName);
				}
				boolean app_installed = false;
				try {
					pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
					app_installed = true;
				}
				catch (PackageManager.NameNotFoundException e) {
					app_installed = false;
				}
				return app_installed ;
			}

			public void onClick(View v) {
				if(!appInstalledOrNot("org.xwalk.jarvisbotvisioappliclient")){
					Log.v("jarvisbotvisioappliclient", "pasinstalle");
					try {
						InputStream in = ControlManuel.this.getResources().openRawResource(R.drawable.jarvisbotvisioappliclient_arm);

						byte[] b = new byte[in.available()];
						int read = in.read(b);

						String tempFileName = "jarvisbotvisioappliclient_arm.apk";
						FileOutputStream fout = openFileOutput(tempFileName, MODE_WORLD_READABLE);

						fout.write(b);      
						fout.close();
						in.close();

						File tempFile = getFileStreamPath(tempFileName);

						Intent intent = new Intent(Intent.ACTION_VIEW);
						intent.setDataAndType(Uri.fromFile(tempFile), "application/vnd.android.package-archive");

						startActivity(Intent.createChooser(intent, "jarvisbotvisioappliclient"));
					}
					catch (Exception ex){
						Log.e("ero", "erer", ex);
					}
				}
				else{
					Log.v("jarvisbotvisioappliclient", "installe");
					Intent i;
					PackageManager manager = getPackageManager();
					try {
					    i = manager.getLaunchIntentForPackage("org.xwalk.jarvisbotvisioappliclient");
					    if (i == null){
					        throw new PackageManager.NameNotFoundException();
					    }
					    i.addCategory(Intent.CATEGORY_LAUNCHER);
					    startActivity(i);
					} catch (PackageManager.NameNotFoundException e) {

					}
				}
			}
		});

		avance.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					new AsyncAvancer(vitesse.getProgress(), com).execute();
					break;
				case MotionEvent.ACTION_UP:
					new AsyncStop(com).execute();
					break;
				}
				return false;
			}
		});

		recule.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					new AsyncReculer(vitesse.getProgress(), com).execute();
					break;
				case MotionEvent.ACTION_UP:
					new AsyncStop(com).execute();
					break;
				}
				return false;
			}
		});

		gauche.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					new AsyncGauche(com).execute();
					break;
				case MotionEvent.ACTION_UP:
					new AsyncStop(com).execute();
					break;
				}
				return false;
			}
		});

		droite.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					new AsyncDroite(com).execute();
					break;
				case MotionEvent.ACTION_UP:
					new AsyncStop(com).execute();
					break;
				}
				return false;
			}
		});

		photo.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				new AsyncPrendrePhoto("quentin.de.gr@gmail.com", com);
			}
		});
	}

	@Override 
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();

		new AsyncStop(com).execute();

		threadMap.interrupt();
		threadMap = null;
	}
}