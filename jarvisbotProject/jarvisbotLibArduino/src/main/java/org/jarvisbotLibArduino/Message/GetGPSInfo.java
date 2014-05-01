package org.jarvisbotLibArduino.Message;

public class GetGPSInfo extends IArduinoMessage {

	public GetGPSInfo(){
		super(IArduinoMessage.RECEIVE_GPSINFO);
	}

}
