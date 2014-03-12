package kercar.comAPI;

/**
 * Commande d'arrêt
 * @author itooh
 */
public class CMDStopMessage extends Message implements ICMDStopMessage {

	public CMDStopMessage() {
		super(Message.CMD_STOP);
	}
	
	/**
	 * (re)Construction de la commande à partir d'un message reçu
	 * @param m
	 */
	public CMDStopMessage(Message m){
		super(Message.CMD_STOP);
		this.params = m.getParams();
	}

}
