package kercar.comAPI.test;

import kercar.comAPI.IRawMessage;
import kercar.comAPI.Message;
import kercar.comAPI.json.JSONParser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*; 



@RunWith(JUnit4.class)
public class JSONTest {
	
	Message m;
	
	@Before
	public void setUp(){
		this.m = new Message(Message.CMD_MOVE);
		this.m.addParam("Test");
	}
	
	@After
	public void tearDown(){
		this.m = null;
	}

	@Test
	public void testJSONDecoding(){
		try{
			JSONParser.decode(this.m.toString());
		} catch(Exception e){
			assertTrue(false);
		}
		assertTrue(true);	
	}
	
	@Test
	public void testJSONDecoding2(){
		IRawMessage m2 = JSONParser.decode(this.m.toString());
		assertEquals(this.m.getParams().get(0), m2.getParams().get(0));
	}
	
}
