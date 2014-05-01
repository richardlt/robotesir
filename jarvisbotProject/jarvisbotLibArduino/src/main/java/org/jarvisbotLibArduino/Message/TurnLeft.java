package org.jarvisbotLibArduino.Message;

public class TurnLeft extends IArduinoMessage {

	public TurnLeft(){
		super(IArduinoMessage.LEFT);
	}
	
	public void setDegree(int degree){
		this.setParam(0, degree);
	}
}
