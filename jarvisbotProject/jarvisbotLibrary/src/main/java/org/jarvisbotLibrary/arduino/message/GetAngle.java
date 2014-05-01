package kercar.raspberry.arduino.message;

public class GetAngle extends IArduinoMessage {

	public GetAngle(){
		super(IArduinoMessage.RECEIVE_ANGLE);
	}
	
	public int getDegree(){
		return toInt(params[0], this.paramSize);
	}

}
