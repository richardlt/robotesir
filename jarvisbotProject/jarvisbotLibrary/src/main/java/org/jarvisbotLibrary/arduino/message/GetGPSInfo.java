package kercar.raspberry.arduino.message;

public class GetGPSInfo extends IArduinoMessage {

	public GetGPSInfo(){
		super(IArduinoMessage.RECEIVE_GPSINFO);
	}

}
