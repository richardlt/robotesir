

package com.kercar.osmandroid;
 
import java.util.List;

import android.graphics.drawable.Drawable;
 
public interface OSMAndroidInterface {
 
	/**
	 * Add a point on the map
	 * @param latitude format E6
	 * @param longitude format E6
	 * @param description
	 * @return the point id, if failure : -1
	 */
	public int addPoint(int latitude, int longitude, String name, String description);
 
	/**
	 * Remove the point
	 * @param id
	 * @return work
	 */
	public boolean removePoint(int id);
 
	/**
	 * Set the visibility of the point
	 * @param id
	 * @param visible
	 */
	public void setPointVisibility(int id, boolean visible);
 
	/**
	 * Set new location for a point
	 * @param id
	 * @param latitude format E6
	 * @param longitude format E6
	 * @return work
	 */
	public boolean setPointLocation(int id, int latitude, int longitude);
 
	/**
	 * Add a road on the map, the view must be updated with invalidate()
	 * @param startPointId 
	 * @param endPointId
	 * @return road id, if failure : -1
	 */
	public int addRoad(int startPointId, int endPointId);
 
	/**
	 * Remove the road, the view must be updated with invalidate()
	 * @param id
	 * @return work
	 */
	public boolean removeRoad(int id);
 
	/**
	 * Set the visibility of the road
	 * @param id
	 * @param visible
	 */
	public void setRoadVisibility(int id, boolean visible);
 
	/**
	 * Set a new color on the road
	 * @param id
	 * @return work
	 */
	public boolean setRoadColor(int id, int color);
 
	/**
	 * Return the step of the road
	 * @param id
	 * @return a list containing the coordinates of the point. First the latitude then the longitude. Null if the road isn't found
	 */
	public List<Integer> getRoadStep(int id);
 
	/**
	 * Go to center
	 */
	public void moveToCenter();
 
	/**
	 * Get the last Road
	 */
	public int getLastRoad();
 
	/**
	 * Return the id of the last startPoint created with a long press 
	 * @return id
	 */
	public int getLastStartPoint();
 
	/**
	 * Return the id of the last endPoint created with a long press 
	 * @return id
	 */
	public int getLastEndPoint();
 
	/**
	 * Return the name of the point
	 * @return name
	 */
	public String getPointName(int id);
 
	/**
	 * Return the description of the point
	 * @return description
	 */
	public String getPointDescription(int id);
 
	/**
	 * Return the latitude of the point
	 * @return latitude format E6
	 */
	public int getPointLatitude(int id);
 
	/**
	 * Return the longitude of the point
	 * @return longitude format E6
	 */
	public int getPointLongitude(int id);
	
	/**
	 * Set a default marker
	 * @param marker
	 */
	public void setDefaultMarker(Drawable marker);
	
	public boolean roadIsFinished(); 
}

