package kercar.comAPI.test;

import kercar.comAPI.Message;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*; 

@RunWith(JUnit4.class)
public class MessageTest {
	
	@Rule
	public ExpectedException noException = ExpectedException.none();
	private Message m;
	private Message m2;
	
	@Before
	public void setUp(){
		m = new Message(Message.CMD_STOP);
		m2 = new Message(Message.CMD_STOP);
		m2.addParam("TestParam");
	}
	
	@After
	public void tearDown(){
		m = null;
		m2 = null;
	}

    @Test
    public void constructTest() {
    	try{
    		new Message(Message.CMD_MOVE);
    	}catch(Exception e){
    		System.err.println("Erreur lors de la construction d'un message");
    	}
    }
    
    @Test
    public void typeTest(){
    	assertTrue(this.m.getType() == Message.CMD_STOP);
    }
    
    @Test
    public void typeTest2(){
    	assertFalse(this.m.getType() == Message.CMD_ERROR);
    }
    
    @Test
    public void addParamTest(){
    	this.m.addParam("Essai");
    	assertTrue(this.m.getParams().size() == 1);
    }
    
    @Test
    public void sizeTest(){
    	assertTrue(this.m.getParams().size() == 0);
    }
    
    @Test
    public void getTest1(){
    	assertTrue(this.m2.getParams().get(0).equals("TestParam"));
    }
    
    @Test
    public void getTest2(){
    	try{
    		this.m2.getParams().get(1);
    		assertTrue(false);
    	} catch(Exception e){
    		assertTrue(true);
    	}
    }
}