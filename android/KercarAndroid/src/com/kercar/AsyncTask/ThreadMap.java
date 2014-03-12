package com.kercar.AsyncTask;

import com.kercar.osmandroid.OSMAndroid;

import kercar.android.IComAndroid;
import kercar.comAPI.IStateMessage;

public class ThreadMap extends Thread{
	
	//Attributs
	private OSMAndroid OSM;
	private IComAndroid com;	
	private IStateMessage stateMessage;
	private int latitude;
	private int longitude;
	private int tmp;
	private int emplacement;
	
	public ThreadMap(OSMAndroid OSM, IComAndroid com){
		this.OSM = OSM;
		this.com = com;
		this.latitude = 0;
		this.longitude = 0;
		this.tmp = 0;
		this.emplacement = 0;
	}
	
	@Override
	public void run() {
		while(true){
			try {
				stateMessage = com.demanderEtat();
				latitude = stateMessage.getLatitude();
				longitude = stateMessage.getLongitude();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//Localisation du robot
			if(tmp != 0){
				OSM.removePoint(emplacement);
			}
				emplacement = OSM.addPoint(latitude, longitude, "Emplacement du robot", "");
			OSM.postInvalidate();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
			tmp = 1;
		}
	}
}