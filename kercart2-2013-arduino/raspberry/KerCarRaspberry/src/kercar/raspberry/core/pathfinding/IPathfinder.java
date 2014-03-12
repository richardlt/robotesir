package kercar.raspberry.core.pathfinding;

import java.util.List;

public interface IPathfinder {

	public void setPath(List<Integer> path);
	public void setSpeed(int speed);
	public void goToNextPoint(int latitude, int longitude, int compass);

	public void updateAngle(int gpsLatitude, int gpsLongitude, int compass);
	public boolean isArrived(int latitude, int longitude);
	public boolean isLastPoint();
}
