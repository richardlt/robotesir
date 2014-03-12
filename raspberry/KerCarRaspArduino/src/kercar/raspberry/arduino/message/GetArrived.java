package kercar.raspberry.arduino.message;

public class GetArrived extends IArduinoMessage {

	public GetArrived(){
		super(IArduinoMessage.RECEIVE_ARRIVED);
	}
}
