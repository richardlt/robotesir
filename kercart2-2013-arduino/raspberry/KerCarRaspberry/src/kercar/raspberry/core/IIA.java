package kercar.raspberry.core;

import java.util.List;


public interface IIA {
	public void turnLeft();
	public void turnRight();
	public void turnLeftAngle(int degree);
	public void turnRightAngle(int degree);
	public void forward(int speed);
	public void backward(int speed);
	public void stopKercar();
	
	public void launchMission(List<Integer> points, String mail, int speed, boolean takePhoto);
	
	public void waitMessage();
	public void stopMission();
	public void setBlocked(boolean blocked);
	public void setAngle(int angle);
	public void setLongitude(int longitude);
	public void setLatitude(int latitude);
	
	public void takePhoto(String mail);
//	public MessageHandler getMessageHandler();
}
