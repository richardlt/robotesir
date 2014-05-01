package kercar.raspberry.arduino.message;

public class AskBlocked extends IArduinoMessage {

	public AskBlocked() {
		super(IArduinoMessage.GETBLOCK);
	}
}
