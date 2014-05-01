package kercar.raspberry.arduino;

import kercar.raspberry.arduino.message.IArduinoMessage;

public class SerialListenerImpl implements SerialListener {

	@Override
	public void onSerialMessage(byte[] data) {
		IArduinoMessage msg = IArduinoMessage.fromBytes(data);
		System.out.println("ID "+msg.getID());
		System.out.println("Param 0 "+msg.getParam(0));
		System.out.println("Param 1 "+msg.getParam(1));
		System.out.println("Binary content : "+ IArduinoMessage.toBinary(data));
		switch(0){
		default:
			break;
		}
	}

}
