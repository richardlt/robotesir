package com.kercar.osmandroid;

import java.util.Iterator;

import android.os.Bundle;
import android.util.Log;
import android.app.Activity;
import android.graphics.Color;

public class MapActivity extends Activity {

	private OSMAndroid osmAndroid;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		osmAndroid = new OSMAndroid(this);
		osmAndroid.setDefaultMarker(this.getResources().getDrawable(R.drawable.marker));
		int id1 = osmAndroid.addPoint(48120002, -1635540, "Le bon point", "Ma jolie description");
			
		setContentView(osmAndroid);
		
		int id2 = osmAndroid.addPoint(48125002, -1654000, "Un autre point", "L'autre description");
		int idRoad = osmAndroid.addRoad(id1, id2);
		osmAndroid.setRoadColor(idRoad, Color.RED);
		
	//	osmAndroid.removePoint(0);
		osmAndroid.addRoad(2, 3);
		
		id1 = osmAndroid.addPoint(48120002, -1635540, "Le bon point", "Ma jolie description");
		int id3 = osmAndroid.addPoint(48117002, -1640540, "Le bon point 2", "Ma jolie description 2");
		
		int road2 = osmAndroid.addRoad(id1, id3);
		osmAndroid.setRoadColor(road2, Color.GRAY);
		
		int id4 = osmAndroid.addPoint(48115002, -1630054, "Un super point", "La description");
		osmAndroid.addRoad(id4, id2);
		
		osmAndroid.setPointVisibility(id3, false);
		osmAndroid.setPointVisibility(id3, true);
		
		osmAndroid.setRoadVisibility(road2, false);
		osmAndroid.setRoadVisibility(road2, true);
		
		Log.e("ERROR", osmAndroid.getPointName(id1));
		Log.e("ERROR", osmAndroid.getPointDescription(id1));
		Log.e("ERROR", Integer.toString(osmAndroid.getPointLatitude(id1)));
		Log.e("ERROR", Integer.toString(osmAndroid.getPointLongitude(id1)));
		
	//	Iterator<Integer> it = osmAndroid.getRoadStep(idRoad).iterator();
		/*
		while(it.hasNext()) {
			osmAndroid.addPoint(it.next(), it.next(), "test", "test");
		}*/
	}
}
