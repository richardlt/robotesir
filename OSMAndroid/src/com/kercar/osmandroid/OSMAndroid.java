package com.kercar.osmandroid;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.osmdroid.bonuspack.overlays.ExtendedOverlayItem;
import org.osmdroid.bonuspack.overlays.ItemizedOverlayWithBubble;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.PathOverlay;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class OSMAndroid extends MapView implements OSMAndroidInterface {

	private Context activity;
	private int idRoad;
	private int idPoint;
	
	private Drawable defaultMarker;
	private SparseArray<ExtendedOverlayItem> spArOverlayItem;
	private SparseArray<PathOverlay> spArPathOverlay;
	private SparseArray<Road> spArRoad;
	private ItemizedOverlayWithBubble<ExtendedOverlayItem> myItemizedIconOverlay;
	private GeoPoint center;
	private boolean isStartPoint;
	private int startPoint, endPoint;
	private int lastRoad;
	private boolean roadIsFinished;
	private GestureDetector gestureDetector;
	
	/**
	 * Map Creation
	 * @param context
	 */
	public OSMAndroid(Context activity) {
		super(activity, 256);
		
		this.activity = activity;
		
		init();
	}
	
	/**
	 * Map Creation
	 * @param context
	 * @param AttributeSet
	 */
	public OSMAndroid(Context activity, AttributeSet attrs) {
		super(activity, attrs);
		
		this.activity = activity;
		
		init();
	}
	
	private void init() {
		//Creation of the map
        this.setClickable(true);
        this.setMultiTouchControls(true);
        
        //No internet
        this.setUseDataConnection(true);
        
        this.getController().setZoom(18);
        
        //Center of the map
        this.center = new GeoPoint(48.12000200, -1.6355400);
        this.getController().setCenter(this.center);
        
        //A lot of initializations
  //      this.defaultMarker = this.activity.getResources().getDrawable(R.drawable.marker);
        
        this.spArOverlayItem = new SparseArray<ExtendedOverlayItem>();
       
        this.spArPathOverlay = new SparseArray<PathOverlay>();
        this.spArRoad = new SparseArray<Road>();
        
        this.myItemizedIconOverlay  = new ItemizedOverlayWithBubble<ExtendedOverlayItem>(this.activity, new ArrayList<ExtendedOverlayItem>(), this);
        this.getOverlays().add(this.myItemizedIconOverlay);
        
        this.idPoint = 0;
        this.idRoad = 0;
        
        this.startPoint = 0;
        this.endPoint = 0;
        this.setRoadIsFinished(false);
        this.setLongClickable(true);
        
        this.isStartPoint = true;
	}


	public int addPoint(int latitude, int longitude, String name,
			String description) {
		return this.creationPoint(name, description, new GeoPoint(latitude, longitude));
		
	}
	
	public int addPointAndRoad(MotionEvent e) {
		GeoPoint point = (GeoPoint) this.getProjection().fromPixels(e.getX(), e.getY());
		if(this.isStartPoint) {
			this.startPoint = this.creationPoint("Depart", "Depart du robot.", point);
			this.isStartPoint = false;
			return this.startPoint;
		} else {
			this.endPoint = this.creationPoint("Arrivee", "Arrivee du robot.", point);
			this.isStartPoint = true;
			this.lastRoad = this.addRoad(this.startPoint, this.endPoint);
			return this.endPoint;
		}
	}
	
	private int creationPoint(String name, String description, GeoPoint point) {
		//Create a visual point
		ExtendedOverlayItem item = new ExtendedOverlayItem(name, description, point, this.activity);
		item.setMarker(this.defaultMarker);
		item.setMarkerHotspot(OverlayItem.HotspotPlace.BOTTOM_CENTER);
		//Add the point at the list point !
		this.myItemizedIconOverlay.addItem(item);
		this.spArOverlayItem.append(idPoint, item);
		
		this.idPoint++;
		return this.idPoint - 1;
	}


	public boolean removePoint(int id) {
		if(spArOverlayItem.get(id) != null) {
			//Get the point and remove it !
			ExtendedOverlayItem item = this.spArOverlayItem.get(id);
			this.spArOverlayItem.delete(id);
			this.myItemizedIconOverlay.removeItem(item);
			return true;
		}
		return false;
	}
	

	public boolean setPointLocation(int id, int latitude, int longitude) {
		if(spArOverlayItem.get(id) != null) {
			GeoPoint point = this.spArOverlayItem.get(id).getPoint();
			point.setLatitudeE6(latitude);
			point.setLongitudeE6(longitude);
			return true;
		}
		return false;
	}


	public int addRoad(int startPointId, int endPointId) {
		if(spArOverlayItem.get(startPointId) == null || spArOverlayItem.get(endPointId) == null) {
			return -1;
		}
		
		try {
			//Create a road between two points, and get this beautiful road 
			this.setRoadIsFinished(false);
			GeoPoint A = this.spArOverlayItem.get(startPointId).getPoint();
			GeoPoint B = this.spArOverlayItem.get(endPointId).getPoint();
//			road = new RoadAsyncTask().execute(A, B).get();
			
			this.idRoad++;
	        new RoadThread(this, A, B).start();
	    //    new RoadAsyncTask(this, A, B).execute();
			//Visual road
			this.lastRoad = idRoad - 1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
        return this.lastRoad;
	}


	public boolean removeRoad(int id) {
		if(spArPathOverlay.get(id) != null) {
			//Remove the road
			this.getOverlays().remove(this.spArPathOverlay.get(id));
			this.spArPathOverlay.remove(id);
			this.spArRoad.remove(id);
			return true;
		} 
		return false;
	}
	
	public void setRoadVisibility(int id, boolean visible) {
		if(spArPathOverlay.get(id) != null) {
			PathOverlay path = this.spArPathOverlay.get(id);
			if(visible) {
				this.getOverlays().add(path);
			} else {
				this.getOverlays().remove(path);
			}
		}
	}
	
	public void setPointVisibility(int id, boolean visible) {
		if(spArOverlayItem.get(id) != null) {
			ExtendedOverlayItem item = this.spArOverlayItem.get(id);
			if(visible) {
				this.myItemizedIconOverlay.addItem(item);
			} else {
				this.myItemizedIconOverlay.removeItem(item);
			}
		}
	}


	public boolean setRoadColor(int id, int color) {
		if(spArPathOverlay.get(id) != null) {
			this.spArPathOverlay.get(id).setColor(color);
			return true;
		}
		return false;
			
	}


	public List<Integer> getRoadStep(int id) {
		if(spArPathOverlay.get(id) != null) {
			ArrayList<GeoPoint> list = spArRoad.get(id).mRouteHigh;
			
			List<Integer> result = new LinkedList<Integer>();
			
			for(int i = 0; i < list.size(); i++) {
				GeoPoint tmp = list.get(i);
				result.add(tmp.getLatitudeE6());
				result.add(tmp.getLongitudeE6());
			}
			
			return result;
		}
		return null;
	}
	
	public void moveToCenter() {
		this.getController().setCenter(this.center);
		this.getController().setZoom(18);
		this.invalidate();
	}
	
	@Override
	public int getLastRoad() {
		return lastRoad;
	}

	@Override 
    public boolean onTouchEvent(MotionEvent event){ 
		if(gestureDetector != null) {
			this.gestureDetector.onTouchEvent(event);
		}
        // Be sure to call the superclass implementation
        return super.onTouchEvent(event);
    }
	
	public void setListener(GestureDetector gesture) {
		this.gestureDetector = gesture;
	}

	@Override
	public int getLastStartPoint() {
		return this.startPoint;
	}

	@Override
	public int getLastEndPoint() {
		return this.endPoint;
	}

	@Override
	public String getPointName(int id) {
		return this.spArOverlayItem.get(id).getTitle();
	}

	@Override
	public String getPointDescription(int id) {
		return this.spArOverlayItem.get(id).getDescription();
	}

	@Override
	public int getPointLatitude(int id) {
		return this.spArOverlayItem.get(id).getPoint().getLatitudeE6();
	}

	@Override
	public int getPointLongitude(int id) {
		return this.spArOverlayItem.get(id).getPoint().getLongitudeE6();
	}

	@Override
	public void setDefaultMarker(Drawable marker) {
		this.defaultMarker = marker;
	}
	
	public int getIdRoad() {
		return this.idRoad;
	}
	
	public SparseArray<Road> getSparRoad() {
		return this.spArRoad;
	}
	
	public SparseArray<PathOverlay> getSparPathOberlay() {
		return this.spArPathOverlay;
	}
	
	public synchronized boolean roadIsFinished() {
		return this.roadIsFinished;
	}
	
	public synchronized void setRoadIsFinished(boolean bool) {
		this.roadIsFinished = bool;
	}
}
