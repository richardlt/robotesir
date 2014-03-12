package kercar.comAPI;

/**
 * Interface de la commande pour faire avancer ou reculer le robot
 * @author itooh
 */
public interface ICMDMoveMessage {
	
	public int getSpeed();
	public void setSpeed(int speed);
	
	/**
	 * Demande s'il s'agit d'une commande pour avancer ou reculer
	 * @return vrai si c'est une commande de recul
	 */
	public boolean isBackward();
	/**
	 * Précise le sens de la commande : marche avant ou recul
	 * @param backward : vrai pour recul
	 */
	public void setBackward(boolean backward);

}
