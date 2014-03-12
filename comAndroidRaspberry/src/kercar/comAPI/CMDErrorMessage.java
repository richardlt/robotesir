package kercar.comAPI;

/**
 * Message d'erreur
 * @author itooh
 */
public class CMDErrorMessage extends Message implements ICMDErrorMessage {

	public static final int INDEX_NUMPACKAGE = 0;
	
	/**
	 * 
	 * @param numPackage : Numéro du paquet
	 */
	public CMDErrorMessage(int numPackage) {
		super(Message.CMD_ERROR);
		
		this.params.add(INDEX_NUMPACKAGE, Integer.toString(numPackage));
	}
	
	/**
	 * (re)Construction de la commande à partir d'un message reçu
	 * @param m
	 */
	public CMDErrorMessage(Message m){
		super(Message.CMD_ERROR);
		this.params = m.getParams();
	}

	@Override
	public int getNumPackage() {
		return Integer.parseInt(this.params.get(INDEX_NUMPACKAGE));
	}

	@Override
	public void setNumPackage(int num) {
		this.params.set(INDEX_NUMPACKAGE, Integer.toString(num));
	}

}
