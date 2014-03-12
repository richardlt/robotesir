package kercar.comAPI;

/**
 * Commande pour faire avancer ou reculer le robot
 * @author itooh
 */
public class CMDMoveMessage extends Message implements ICMDMoveMessage {

	public static final int INDEX_SPEED = 0;
	public static final int INDEX_BACKWARD = 1;
	
	/**
	 * @param speed
	 * @param backward : Vrai pour une commande de recul, faux sinon
	 */
	public CMDMoveMessage(int speed, boolean backward) {
		super(Message.CMD_MOVE);
		
		this.params.add(INDEX_SPEED, Integer.toString(speed));
		this.params.add(INDEX_BACKWARD, Boolean.toString(backward));
	}
	
	 /**
	  * (re)Construction de la commande à partir d'un message reçu
	  * @param m
	  */
	public CMDMoveMessage(Message m){
		super(Message.CMD_MOVE);
		this.params = m.getParams();
	}

	@Override
	public int getSpeed() {
		return Integer.parseInt(this.params.get(INDEX_SPEED));
	}

	@Override
	public void setSpeed(int speed) {
		this.params.set(INDEX_SPEED, Integer.toString(speed));
	}

	@Override
	public boolean isBackward() {
		return Boolean.parseBoolean(this.params.get(INDEX_BACKWARD));
	}

	@Override
	public void setBackward(boolean backward) {
		this.params.set(INDEX_BACKWARD, Boolean.toString(backward));
	}

}
