package kercar.comAPI.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

public class TestSuite {

	
	@RunWith(Suite.class)
	@SuiteClasses(value={
		MessageTest.class,
		JSONTest.class,
		StateTest.class
	})
	public class AllTests{
	}
	
}
