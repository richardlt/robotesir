package kercar.comAPI;

public class PingMessage extends Message implements IPingMessage {
	
	public PingMessage() {
		super(Message.PING);
	}
	
	/**
	 * (re)Construction de la commande à partir d'un message reçu
	 * @param m
	 */
	public PingMessage(Message m){
		super(Message.PING);
		this.params = m.getParams();
	}

}
