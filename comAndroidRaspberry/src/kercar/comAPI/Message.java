package kercar.comAPI;

import java.util.ArrayList;
import java.util.List;

import kercar.comAPI.json.JSONParser;

/**
 * Classe représentant un message envoyé depuis le Raspberry et l'Arduino
 * Sert notamment à contenir les paramètres du message
 * La construction d'un tel objet est donc réservée au parser JSON
 * @author Kid
 *
 */
public class Message implements IRawMessage, IMessage {
	
	public static final int COM_WIFI = -1;
	public static final int COM_BLUETOOTH = -2;

	/**
	 * Types possible de message
	 */
	public static final int CMD_ERROR = 0;
	public static final int CMD_STOP = 1;
	public static final int CMD_MOVE = 2;
	public static final int CMD_TURN = 3;
	public static final int CMD_PHOTO = 4;
	public static final int CMD_MISSION = 5;
	public static final int GET_STATE = 6;
	public static final int STATE = 7;
	public static final int PING = 8;
	
	/** Type du message, valeur définie parmis les variables globales de type */
	private final int type;
	/** Paramètres du message */
	protected List<String> params;
	
	/**
	 * Le numero du Message niveau packet
	 */
	private int numMessage;
	
	/**
	 * Appelé uniquement par le parser JSon (et classes filles), pour construire un message "abstrait"
	 * @param type
	 */
	public Message(int type){
		this.type = type;
		this.params = new ArrayList<String>();
	}
	
	@Override
	public int getType(){
		return this.type;
	}
	
	/**
	 * La liste des paramètres du message ne doit être accessible que lors du transfert
	 */
	@Override
	public List<String> getParams(){
		return this.params;
	}
	
	@Override
	public void addParam(String param){
		this.params.add(param);
	}
	
	@Override
	public int getMessageNum() {
		return this.numMessage;
	}
	
	@Override
	public void setMessageNum(int num){
		this.numMessage = num;
	}
	
	@Override
	public String toString(){
		return JSONParser.encode(this);
	}
}
