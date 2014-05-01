package kercar.raspberry.arduino.message;

public class AskGPSInfo extends IArduinoMessage {

	public AskGPSInfo(){
		super(IArduinoMessage.GETGPSINFO);
	}

}
