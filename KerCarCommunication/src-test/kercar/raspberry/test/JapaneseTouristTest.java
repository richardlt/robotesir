package kercar.raspberry.test;

import kercar.raspberry.core.JapaneseTourist;

public class JapaneseTouristTest {

	public static void main(String[] args){
		try{
			testTakePhotos();
			testClearPhotos();
			testTakePhotos();
			testSendMail();
			testSendLog();
			System.out.println("YATTA !");
		} catch(Exception e){
			System.out.println("AYOUYOUYOUYOUYOU :(");
		}
	}
	
	public static void testTakePhotos(){
		JapaneseTourist.takePhoto();
	}
	
	public static void testClearPhotos(){
		JapaneseTourist.clearPhotos();
	}
	
	public static void testSendMail(){
		JapaneseTourist.sendPhotos();
	}
	
	public static void testSendLog(){
		JapaneseTourist.sendLogs();
	}
}
