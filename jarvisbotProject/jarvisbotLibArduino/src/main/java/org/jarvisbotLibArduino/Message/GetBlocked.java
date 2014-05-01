package org.jarvisbotLibArduino.Message;

public class GetBlocked extends IArduinoMessage {

	public GetBlocked(){
		super(IArduinoMessage.RECEIVE_BLOCK);
	}
	
	public boolean isBlocked() {
		return toInt(params[0], this.paramSize) == 1;
	}

}
