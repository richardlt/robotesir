package kercar.raspberry.core.pathfinding;

import java.util.Iterator;
import java.util.List;

import kercar.raspberry.core.Core;
import kercar.raspberry.core.IIA;


public class Pathfinder implements IPathfinder {

	private IIA iA;
	private Iterator<Integer> it;
	
	private int pointLatitude = 0, pointLongitude = 0;
	private int currentSpeed = 0;
	
	public Pathfinder(IIA iA) {
		this.iA = iA;
	}
	
	@Override
	public void setPath(List<Integer> path) {
		this.it = path.iterator();
	}

	@Override
	public void goToNextPoint(int gpsLatitude, int gpsLongitude, int compass) {
		System.out.println("PathFinder : NEXT POINT");
		Core.Log("PathFinder : NEXT POINT");
		
		this.pointLatitude = it.next();
		this.pointLongitude = it.next();
		int angle = this.calculateAngle(gpsLatitude, gpsLongitude, compass);
		
		if(angle < 180) 
			this.iA.turnRightAngle(angle);
		else
			this.iA.turnLeftAngle(360 - angle);
		
	//	this.iA.waitMessage();
		
		this.iA.forward(currentSpeed);
	}
	
	public void updateAngle(int gpsLatitude, int gpsLongitude, int compass) {
		System.out.println("PathFinder : UPDATING ANGLE");
		Core.Log("PathFinder : UPDATING ANGLE");
		
		int angle = this.calculateAngle(gpsLatitude, gpsLongitude, compass);
		
		boolean turn = false;
		if((angle >= compass && (pourcent(compass, angle) <= 90)) || (pourcent(angle, compass) <= 90)) {
			if(turn) {
				if(angle < 180) 
					this.iA.turnRightAngle(angle);
				else
					this.iA.turnLeftAngle(360 - angle);
//				this.iA.waitMessage();
			}	
		} 		
		this.iA.forward(this.currentSpeed);
	}
	
	private int calculateAngle(int gpsLatitude, int gpsLongitude, int compass) {	
		//Calcul de a²
		double a = Math.pow(pointLongitude - gpsLongitude, 2) + Math.pow(pointLatitude - gpsLatitude, 2);
		System.out.println("a " + a);
		
		//Calcul de b²
		int YNord = gpsLatitude + 5;
		int XNord = gpsLongitude;
		double b = Math.pow(XNord - gpsLongitude, 2) + Math.pow(YNord - gpsLatitude, 2);
		System.out.println("c " + b);
		
		//Calcul de c²	
		double c = Math.pow(pointLongitude - XNord, 2) + Math.pow(pointLatitude - YNord, 2);
		System.out.println("b " + c);
		
		//Al kashi
		//TODO Attention si a +b  > c à voir si BOUMBOUM
		double angleBetaRadian =  Math.acos(Math.abs(a + b - c) / (2 * Math.sqrt(a) * Math.sqrt(b)));
		int angleBeta = (int) ((180 * angleBetaRadian) / Math.PI);
		System.out.println("angleBeta " + angleBeta);
		
		//On retourne un angle de la position du robot jusqu'à sa destination. Angle toujours vers la droite !
		if(compass <= 180) {
			if(pointLongitude >= XNord)
				return compass + angleBeta;
			else if (angleBeta >= compass)
				return 360 - (angleBeta - compass);
			else
				return compass - angleBeta;
		} else {
			compass = 360 - compass;
			if(pointLongitude <= XNord)
				return 360 - (compass + angleBeta);
			else if (angleBeta >= compass)
				return angleBeta - compass;
			else
				return 360 - (compass - angleBeta);
		}			
	}

	@Override
	public void setSpeed(int speed) {
		this.currentSpeed = speed;
	}

	@Override
	public boolean isArrived(int gpsLatitude, int gpsLongitude) {
		if((gpsLatitude >= pointLatitude && (pourcent(pointLatitude, gpsLatitude) >= 90)) || (pourcent(gpsLatitude, pointLatitude) >= 90)) {
			if((gpsLongitude >= pointLongitude && (pourcent(pointLongitude, gpsLongitude) >= 90)) || (pourcent(pointLatitude, gpsLongitude) >= 90))
				return true;
		}
			
		return false;
	}
	
	@Override
	public boolean isLastPoint() {
		return !it.hasNext(); 
	}
	
	private double pourcent(int first, int second) {
		//TODO Gestion des zeros
		if(first == 0 && second == 0)
			return 100;
		else if(second == 0)
			return 0;
		return (100 * first) / second;
	}

}
