package kercar.comAPI;

/**
 * Interface des messages pour les utilisateurs externes
 * (cad qui ne sont pas impliqués dans le transfert)
 * @author itooh
 */
public interface IMessage {
	
	/**
	 * Le type de commande
	 * @return un entier définissant le type de commande
	 */
	public int getType();

	/**
	 * Préciser le numéro du message niveau packet
	 * @param num
	 */
	void setMessageNum(int num);

}
