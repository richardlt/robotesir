package kercar.raspberry.arduino.message;

public class TurnLeftAngle extends IArduinoMessage {

	public TurnLeftAngle() {
		super(IArduinoMessage.LEFTANGLE);
	}
	
	public void setDegree(int angle) {
		this.setParam(0, angle);
	}
}