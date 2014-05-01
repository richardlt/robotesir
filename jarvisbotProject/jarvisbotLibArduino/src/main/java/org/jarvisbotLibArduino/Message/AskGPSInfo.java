package org.jarvisbotLibArduino.Message;

public class AskGPSInfo extends IArduinoMessage {

	public AskGPSInfo(){
		super(IArduinoMessage.GETGPSINFO);
	}

}
