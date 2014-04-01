package com.kercar.osmandroid;

import java.util.ArrayList;

import org.osmdroid.bonuspack.routing.MapQuestRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.PathOverlay;

public class RoadThread extends Thread {

	private OSMAndroid osm;
	private GeoPoint start, end;
	
	public RoadThread(OSMAndroid osm, GeoPoint start, GeoPoint end) {
		this.osm = osm;
		this.start = start;
		this.end = end;
	}
	
	public void run() {
		RoadManager roadManager = new MapQuestRoadManager();
        roadManager.addRequestOption("routeType=bicycle");

		//Two points added
		ArrayList<GeoPoint> waypoints = new ArrayList<GeoPoint>();
		waypoints.add(start);
		waypoints.add(end);
		Road road = roadManager.getRoad(waypoints);
		// Let's go the mall !
		PathOverlay roadOverlay = RoadManager.buildRoadOverlay(road, osm.getContext());
		osm.getOverlays().add(roadOverlay);
		osm.getSparPathOberlay().append(osm.getIdRoad() - 1, roadOverlay);
		osm.getSparRoad().append(osm.getIdRoad() - 1, road);
		this.osm.setRoadIsFinished(true);
	}
}
