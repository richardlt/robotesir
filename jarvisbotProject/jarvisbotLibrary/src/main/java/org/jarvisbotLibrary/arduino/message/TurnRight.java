package kercar.raspberry.arduino.message;

public class TurnRight extends IArduinoMessage {

	public TurnRight(){
		super(IArduinoMessage.RIGHT);
	}
	
	public void setDegree(int degree){
		this.setParam(0, degree);
	}
}
