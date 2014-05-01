package org.jarvisbotLibArduino;

public interface SerialListener {

	public void onSerialMessage(byte[] message);
}
