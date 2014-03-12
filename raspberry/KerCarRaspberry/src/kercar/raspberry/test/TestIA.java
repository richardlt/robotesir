package kercar.raspberry.test;

import java.util.LinkedList;
import java.util.List;

import kercar.comAPI.CMDMissionMessage;
import kercar.comAPI.CMDMoveMessage;
import kercar.comAPI.CMDStopMessage;
import kercar.comAPI.CMDTurnMessage;
import kercar.raspberry.core.Core;

public class TestIA {
	
	public static void main(String args[]) {
		
		System.out.println("CALCUL ANGLE");
		System.out.println(testCalculAngle(7, 4, 3, 1, 45) == 98);
		System.out.println(testCalculAngle(2, 4, 6, 1, 106) == 53);
		System.out.println(testCalculAngle(1, 1, 4, 2, 23) == 312);
		System.out.println(testCalculAngle(1, 4, 4, 1, 295) == 250);
		System.out.println(testCalculAngle(4, 5, 2, 1, 295) == 321);
		System.out.println(testCalculAngle(5, 0, 2, 1, 315) == 26);
		
		System.out.println("GPS COMPATIBLE DATA");
		System.out.println(toGPSCompatibleData(301043240) == 30173873);
		System.out.println(toGPSCompatibleData(301043241) == -30173873);
		System.out.println(toGPSCompatibleData(21043241) == -2173873);
		/*
		 * Commenter les lignes concernant le serial manager pour les tests dans Core
		 * Décommenter updateAnglet, goToNextPoint et isArrived dans Core pour ne pas utiliser les valeurs de l'arduino
		 */
		/*
		Core core = new Core(".");
		core.start();
		
		core.messageReceived(new CMDStopMessage());
		CMDMoveMessage moveBackward = new CMDMoveMessage(50, true);
		core.messageReceived(moveBackward);
		CMDMoveMessage moveForward = new CMDMoveMessage(50, false);
		core.messageReceived(moveForward);
		CMDTurnMessage turnLeft = new CMDTurnMessage(false);
		core.messageReceived(turnLeft);
		CMDTurnMessage turnRight = new CMDTurnMessage(true);
		core.messageReceived(turnRight);
		
		List<Integer> list = new LinkedList<Integer>();
		list.add(0);
		list.add(0);
		CMDMissionMessage mission = new CMDMissionMessage(list, false, false, "miaou");
		core.messageReceived(mission);
		core.messageReceived(new CMDStopMessage());
		
		list.clear();
		list.add(1);
		list.add(1);
		list.add(0);
		list.add(0);
		mission = new CMDMissionMessage(list, false, false, "miaou");
		core.messageReceived(mission);*/
		
	}
	
	private static int toGPSCompatibleData(int data) {
		//2 chiffres les plus à gauche : degré
		//2 suivant : minutes
		//4 suivant : décimales minutes
		//le plus à droite : orientation : N = O, S = 1, E = 2, W = 3
		
		/*301043242
		DD=Degré+min/60+sec/3600
		30 = degré
		10,4324 = minutes
		2 = E*/
		try {
			String tmp = String.valueOf(data);
			
			if(tmp.length() == 8)
				tmp = "0" + tmp;
			double degree = Integer.parseInt(tmp.substring(0, 2));
			double minutes = Integer.parseInt(tmp.substring(2, 4));
			double mdec = Integer.parseInt(tmp.substring(4, 8));
			minutes = minutes + (mdec/10000);
			
			int letter = Integer.parseInt(tmp.substring(8, 9));
			
			int result = (int) ((degree + (minutes / 60.0))* 1E6);  
			if(letter == 1 || letter == 3)
				return (-result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static int testCalculAngle(int pointLongitude, int pointLatitude, int gpsLongitude, int gpsLatitude, int compass) {
		
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
}
