package kercar.comAPI.test;

import static org.junit.Assert.*;
import kercar.android.ComAndroid;
import kercar.comAPI.IRawMessage;
import kercar.comAPI.IStateMessage;
import kercar.comAPI.Message;
import kercar.comAPI.StateMessage;
import kercar.comAPI.json.JSONParser;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class StateTest {
	
	private IStateMessage stateMessage;
	private static String ADRESS = "http://kercar2013.no-ip.biz:8080/KerCarCommunication/";

	@Before
	public void setUp() throws Exception {
		this.stateMessage = new StateMessage(3,4,5,false, false);
	}

	@Test
	public void testParams() {
		assertTrue(this.stateMessage.getLongitude() == 3);
		assertTrue(this.stateMessage.getLatitude() == 4);
		assertTrue(this.stateMessage.getOrientation() == 5);
	}
	
	@Test
	public void testJson() {
		String json = stateMessage.toString();
		Message msg = (Message) JSONParser.decode(json);
		IStateMessage decodedMessage = new StateMessage(msg);
		assertTrue(decodedMessage.getLongitude() == stateMessage.getLongitude());
		assertTrue(msg.getParams().containsAll(((IRawMessage) stateMessage).getParams()));
	}
	
	@Test
	public void testWrongParameters() {
		Message m = new Message(Message.STATE);
		IStateMessage sm = new StateMessage(m);
		
		try {
			sm.getLatitude();
			fail();
		}
		catch (IndexOutOfBoundsException e) {}
	}
	
	@Test
	public void testConnexion() {
		try {
			ComAndroid ca = ComAndroid.getManager();
			ca.setURL(ADRESS);
			ca.demanderEtat();
		}
		catch (Exception e) {
			fail();
		}
	}

}
