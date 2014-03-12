package kercar.comAPI;

/**
 * Commande de virage
 * @author itooh
 */
public class CMDTurnMessage extends Message implements ICMDTurnMessage {
	
	public static final int INDEX_RIGHT = 0;
	
	/**
	 * Création d'une commande de virage
	 * @param right : Vrai pour un virage à droite, faux pour à gauche
	 */
	public CMDTurnMessage(boolean right) {
		super(Message.CMD_TURN);
		
		this.params.add(INDEX_RIGHT, Boolean.toString(right));
	}
	
	/**
	 * (re)Construction de la commande à partir d'un message reçu
	 * @param m
	 */
	public CMDTurnMessage(Message m){
		super(Message.CMD_TURN);
		this.params = m.getParams();
	}

	@Override
	public boolean isTurningRight() {
		return Boolean.parseBoolean(this.params.get(INDEX_RIGHT));
	}

	@Override
	public void turnRight() {
		this.params.set(INDEX_RIGHT, Boolean.toString(true));
	}

	@Override
	public void turnLeft() {
		this.params.set(INDEX_RIGHT, Boolean.toString(false));
	}

}
