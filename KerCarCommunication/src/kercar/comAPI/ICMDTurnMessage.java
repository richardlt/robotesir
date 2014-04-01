package kercar.comAPI;

/**
 * Interface de la commande de virage
 * @author itooh
 */
public interface ICMDTurnMessage {
	
	/**
	 * Demande la direction du virage
	 * @return vrai si le virage est vers la droite, gauche sinon
	 */
	public boolean isTurningRight();
	public void turnRight();
	public void turnLeft();

}
