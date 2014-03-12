package kercar.android;

import kercar.comAPI.CMDMoveMessage;
import kercar.comAPI.CMDStopMessage;

public class testMsg {

	
	public static void main(String[] args){
		
		String URL = "http://kercar2013.no-ip.biz:8080/KerCarCommunication/";
		IComAndroid com = ComAndroid.getManager();
		com.setURL(URL);
		
		//CMDMoveMessage cmdCommand = new CMDMoveMessage(25, false);
		CMDStopMessage cmdCommand = new CMDStopMessage();
		try {
			System.out.println("Envoi du message");
			com.envoyerMessage(cmdCommand);
			System.out.println(com.lireReponse());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
