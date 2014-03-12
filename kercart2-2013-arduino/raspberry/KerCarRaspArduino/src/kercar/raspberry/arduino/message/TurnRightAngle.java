package kercar.raspberry.arduino.message;

public class TurnRightAngle extends IArduinoMessage {

	public TurnRightAngle() {
		super(IArduinoMessage.RIGHTANGLE);
	}
	
	public void setDegree(int angle) {
		this.setParam(0, angle);
	}
}
