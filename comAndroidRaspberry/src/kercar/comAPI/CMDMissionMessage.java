package kercar.comAPI;

import java.util.ArrayList;
import java.util.List;

/**
 * Commande pour faire avancer ou reculer le robot
 * @author itooh
 */
public class CMDMissionMessage extends Message implements ICMDMissionMessage {

	public static final int COORDS_INDEX = 0;
	public static final int PHOTO_INDEX = 1;
	public static final int BACK_INDEX = 2;
	public static final int EMAIL_INDEX = 3;
	
	/**
	 * @param speed
	 * @param backward : Vrai pour une commande de recul, faux sinon
	 */
	public CMDMissionMessage(List<Integer> coords, boolean takePhoto, boolean goBack, String email) {
		super(CMD_MISSION);
		
		// Creation d'un string avec tous les params
		StringBuilder sb = new StringBuilder();
		for(Integer i : coords){
			sb.append(String.valueOf(i)+",");
		}
		this.params.add(COORDS_INDEX, sb.toString());
		this.params.add(PHOTO_INDEX, String.valueOf(takePhoto));
		this.params.add(BACK_INDEX, String.valueOf(goBack));
		this.params.add(EMAIL_INDEX, email);
	}
	
	 /**
	  * (re)Construction de la commande à partir d'un message reçu
	  * @param m
	  */
	public CMDMissionMessage(Message m){
		super(Message.CMD_MISSION);
		this.params = m.getParams();
	}

	@Override
	public List<Integer> getCoordinates() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		String s = this.params.get(COORDS_INDEX);
		String[] coords = s.split(",");
		for(String c : coords){
			list.add(Integer.valueOf(c));
		}
		return list;
	}

	@Override
	public boolean getPhoto() {
		return Boolean.valueOf(this.params.get(PHOTO_INDEX));
	}

	@Override
	public boolean getBack() {
		return Boolean.valueOf(this.params.get(BACK_INDEX));
	}

	@Override
	public String getMailAddress() {
		return this.params.get(EMAIL_INDEX);
	}
}
