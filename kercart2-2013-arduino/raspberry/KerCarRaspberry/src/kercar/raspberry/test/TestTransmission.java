package kercar.raspberry.test;

import kercar.comAPI.CMDMoveMessage;
import kercar.raspberry.core.Core;

public class TestTransmission {

	public static void main(String args[]) {
		Core core = new Core(".");
		core.start();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		core.messageReceived(new CMDMoveMessage(50, true));
	}
}
