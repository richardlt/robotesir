package com.kercar.AsyncTask;

import com.kercar.osmandroid.OSMAndroid;

import kercar.android.IComAndroid;
import kercar.comAPI.IStateMessage;
import android.os.AsyncTask;
import android.widget.TextView;

public class AsyncGetEtat extends AsyncTask<Void, Integer, Void> {
	//Attributs
	private int latitude;
	private int longitude;
	private int orientation;
	
	private TextView latitudeEdit;
	private TextView longitudeEdit;
	private TextView boussoleEdit;
	
	private IComAndroid comAndroid;
	private OSMAndroid OSM;
	
	private int emplacement;
	private int tmp;
	private boolean stop;

	private IStateMessage stateMessage;
	
	//Constructeurs
	public AsyncGetEtat(IComAndroid comAndroid, TextView latitude, TextView longitude, TextView boussole, OSMAndroid OSM) {
		//Initialisation des attributs
		this.latitude = 0;
		this.longitude = 0;
		this.orientation = 0;
		
		this.latitudeEdit = latitude;
		this.longitudeEdit = longitude;
		this.boussoleEdit = boussole;
		
		this.comAndroid = comAndroid;
		this.OSM = OSM;
		
		this.emplacement = 0;
		this.tmp = 0;		
		this.stop = false;
	}
	
	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		
		//Donnees du robot
		latitudeEdit.setText(""+values[0]);
		longitudeEdit.setText(""+values[1]);
		boussoleEdit.setText(""+values[2]);
		
		//Localisation du robot
		if(tmp != 0){
			OSM.removePoint(emplacement);
		}
		emplacement = OSM.addPoint(values[0], values[1], "Emplacement du robot", "");
		OSM.invalidate();
		tmp = 1;
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		try {
			while(!stop){
				stateMessage = this.comAndroid.demanderEtat();
				latitude = stateMessage.getLatitude();
				longitude = stateMessage.getLongitude();
				orientation = stateMessage.getOrientation();

				publishProgress(new Integer[]{latitude,longitude,orientation});
				Thread.sleep(10000);
			}
		} catch (Exception e) {e.printStackTrace();}
		return null;
	}
	
	@Override
	protected void onCancelled(){
		stop = true;
	}
}