package kercar.raspberry.arduino.message;

public class AskPos extends IArduinoMessage {

	public AskPos(){
		super(IArduinoMessage.GETPOS);
	}

}
