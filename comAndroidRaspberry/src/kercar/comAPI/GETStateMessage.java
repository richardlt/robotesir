package kercar.comAPI;

public class GETStateMessage extends Message implements IGETStateMessage {

	public GETStateMessage() {
		super(Message.GET_STATE);
	}
	
	/**
	 * (re)Construction de la commande à partir d'un message reçu
	 * @param m
	 */
	public GETStateMessage(Message m) {
		super(Message.GET_STATE);
		this.params = m.getParams();
	}

}
