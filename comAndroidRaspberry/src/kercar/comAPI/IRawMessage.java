package kercar.comAPI;

import java.util.List;

/**
 * Interface des messages pour les composants gérant le transfert des messages
 * @author itooh
 */
public interface IRawMessage {

	/*
	 * Le type de Message (bluetooth vs HTTP)
	 * @return
	 */
	//public int getCom();
	
	/**
	 * Transforme le message en string JSON
	 * @return un String au format JSON
	 */
	public String toString();
	
	/**
	 * Recupere le numero du message
	 * @return un entier indiquant le numero
	 */
	public int getMessageNum();
	
	/**
	 * Recupere les parametres du message
	 * @return Une liste de parametres
	 */
	public List<String> getParams();
	
	/**
	 * Ajoute un parametre au message
	 * À n'appeler que si l'on est certain que les paramètres ajoutés sont dans le bon ordre
	 * @param param Le parametre a ajouter
	 */
	public void addParam(String param);
	
	/**
	 * Le type de commande
	 * @return un entier définissant le type de commande
	 */
	public int getType();

}
