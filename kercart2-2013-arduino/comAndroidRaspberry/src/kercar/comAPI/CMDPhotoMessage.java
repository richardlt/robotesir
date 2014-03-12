package kercar.comAPI;

/**
 * Commande de prise de photo
 * @author itooh
 */
public class CMDPhotoMessage extends Message implements ICMDPhotoMessage {

	public static final int INDEX_ADDRESS = 0; 
	
	/**
	 * @param mailAddress : Adresse vers laquelle envoyer la photo
	 */
	public CMDPhotoMessage(String mailAddress) {
		super(Message.CMD_PHOTO);
		
		this.params.add(INDEX_ADDRESS, mailAddress);
	}
	
	/**
	 * (re)Construction de la commande à partir d'un message reçu
	 * @param m
	 */
	public CMDPhotoMessage(Message m){
		super(Message.CMD_PHOTO);
		this.params = m.getParams();
	}

	@Override
	public String getMailAddress() {
		return this.params.get(INDEX_ADDRESS);
	}

	@Override
	public void setMailAdress(String address) {
		this.params.set(INDEX_ADDRESS, address);
	}

}
