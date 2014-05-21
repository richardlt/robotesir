package org.jarvisbotAndroidClient.AsyncTask;

import java.util.LinkedList;
import org.jarvisbotAndroidClient.OSMAndroid;
import org.jarvisbotLibrary.android.IComAndroid;
import org.jarvisbotLibrary.comApi.IStateMessage;

import android.os.AsyncTask;

public class AsyncGetEtatDeuxPoints extends AsyncTask<Void, Integer, Void> {
	//Attributs
	private int latitude;
	private int longitude;
	private int orientation;
	
	private IComAndroid comAndroid;
	private OSMAndroid OSM;
	
	private int emplacement;
	private int arrive;
	private int route;
	private int tmp;
	private boolean stop;

	private IStateMessage stateMessage;
	
	//Constructeurs
	public AsyncGetEtatDeuxPoints(LinkedList<Integer> list, IComAndroid comAndroid, OSMAndroid OSM, int id) {
		//Initialisation des attributs
		this.latitude = 0;
		this.longitude = 0;
		this.orientation = 0;
		
		this.comAndroid = comAndroid;
		this.OSM = OSM;
		
		this.emplacement = 0;
		this.arrive = id;
		this.route = 0;
		this.tmp = 0;		
		this.stop = false;
	}
	
	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		
		//Localisation du robot
		if(tmp != 0){
			OSM.removePoint(emplacement);
			OSM.removeRoad(route);
		}
		emplacement = OSM.addPoint(values[0], values[1], "Emplacement du robot", "");
		route = OSM.addRoad(emplacement, arrive);
		OSM.invalidate();
		OSM.getRoadStep(route);
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