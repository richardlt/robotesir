package kercar.raspberry.arduino.message;

public class GetPos extends IArduinoMessage {

	public GetPos(){
		super(IArduinoMessage.RECEIVE_POS);
	}
	
	public int getLongitude(){
		return toInt(params[0], this.paramSize);
	}
	
	public int getLatitude(){
		return toInt(params[1], this.paramSize);
	}
}
