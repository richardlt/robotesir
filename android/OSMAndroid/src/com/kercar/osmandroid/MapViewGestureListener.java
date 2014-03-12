package com.kercar.osmandroid;

import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

public class MapViewGestureListener extends SimpleOnGestureListener {

	private OSMAndroid osmAndroid;
	
	public MapViewGestureListener(OSMAndroid osmAndroid) {
		super();
		this.osmAndroid = osmAndroid;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		this.osmAndroid.addPointAndRoad(e);
		this.osmAndroid.invalidate();
	}
}
