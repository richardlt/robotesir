package org.jarvisbotLibArduino.Message;

public class GoForward extends IArduinoMessage {

	public GoForward(){
		super(IArduinoMessage.FORWARD);
	}
	
	public void setVitesse(int speed){
		this.setParam(0, speed);
	}
}
