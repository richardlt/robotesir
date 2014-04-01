package kercar.raspberry.arduino;

public interface SerialListener {

	public void onSerialMessage(byte[] message);
}
