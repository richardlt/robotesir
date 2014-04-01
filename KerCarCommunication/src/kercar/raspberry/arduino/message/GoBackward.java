package kercar.raspberry.arduino.message;

public class GoBackward extends IArduinoMessage {

	public GoBackward(){
		super(IArduinoMessage.BACKWARD);
	}
	
	public void setVitesse(int speed){
		this.setParam(0, speed);
	}
}
