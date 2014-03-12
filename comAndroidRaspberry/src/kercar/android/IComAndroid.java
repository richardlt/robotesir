package kercar.android;

import kercar.comAPI.IStateMessage;
import kercar.comAPI.Message;

/**
 * Interface pour la gestion d'envoi de message depuis Android
 * @author itooh
 */
public interface IComAndroid {
	
	/**
	 * Définit l'adresse URL du Raspberry PI
	 * @param address L'URL du Raspberry PI
	 */
	public void setURL(String address);
	
	/**
	 * Envoie un message au Raspberry PI
	 * @param Message  Le Message a envoyer
	 * @throws Exception En cas de problème à l'envoi
	 */
	public void envoyerMessage(Message message) throws Exception;
	
	/**
	 * Lit la réponse du Raspberry Pi
	 * @return La réponse du Raspberry sous forme de String
	 * @throws Exception En cas d'erreur de lecture
	 */
	public String lireReponse() throws Exception;
	
	/**
	 * Récupère l'état du robot KerCar
	 * @return Message d'état renvoyé par le Raspberry
	 */
	public IStateMessage demanderEtat() throws Exception;

}
