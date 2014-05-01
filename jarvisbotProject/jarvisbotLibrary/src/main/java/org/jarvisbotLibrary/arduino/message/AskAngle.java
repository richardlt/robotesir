package kercar.raspberry.arduino.message;

public class AskAngle extends IArduinoMessage {

	public AskAngle(){
		super(IArduinoMessage.GETANGLE);
	}

}
