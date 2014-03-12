package kercar.comAPI;

import java.util.List;

/**
 * Interface de la commande pour faire avancer ou reculer le robot
 * @author itooh
 */
public interface ICMDMissionMessage {
	
	/**
	 * Renvoie la liste des coordonnées
	 * Forme de la liste : long0 / lat0 / long1 / lat1 / ...
	 * @return Une liste d'entier
	 */
	public List<Integer> getCoordinates();
	
	/**
	 * Renvoie la demande de prise de photo
	 * @return true si une photo doit être prise
	 */
	public boolean getPhoto();
	
	/**
	 * Renvoie le fait que le robot revienne ou non
	 * @return true si le robot doit faire demi-tour
	 */
	public boolean getBack();
	
	/**
	 * Renvoie l'adresse email du destinataire de la photo
	 * @return
	 */
	public String getMailAddress();

}
