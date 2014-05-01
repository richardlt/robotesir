package kercar.raspberry.arduino;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import kercar.raspberry.arduino.message.IArduinoMessage;



public class SerialManager implements SerialPortEventListener {
	SerialPort serialPort;
    
	/** The port we're normally going to use. */
	private static final String PORT_NAMES[] = {"/dev/ttyUSB0"};
	/**
	* A BufferedReader which will be fed by a InputStreamReader 
	* converting the bytes into characters 
	* making the displayed results codepage independent
	*/
	private InputStream input;
	
	private byte[] buffer = new byte[9];
	private byte[] tmp = new byte[9];
	private int bytesRead = 0;
	
	/** The output stream to the port */
	public OutputStream output;
	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	private static final int DATA_RATE = 9600;
	
	private SerialListener listener;

	public void initialize() {
		System.out.println("Initializing SerialManager");
		CommPortIdentifier portId = null;
		@SuppressWarnings("rawtypes")
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

		//First, Find an instance of serial port as set in PORT_NAMES.
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					break;
				}
			}
		}
		if (portId == null) {
			System.out.println("Could not find COM port.");
			return;
		}

		try {
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(),
					TIME_OUT);

			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			// open the streams
			input = serialPort.getInputStream();
			output = serialPort.getOutputStream();

			// add event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	/**
	 * This should be called when you stop using the port.
	 * This will prevent port locking on platforms like Linux.
	 */
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	/**
	 * Handle an event on the serial port. Read the data and print it.
	 */
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				int tmpRead = input.read(tmp);
				//System.out.println("Octets lu :" + tmpRead);
				System.arraycopy(tmp, 0, buffer, bytesRead, tmpRead);
				bytesRead += tmpRead;
				
				if(bytesRead == 9){
					System.out.println("Message received from Arduino");
					bytesRead = 0;
					if(this.listener != null)
						this.listener.onSerialMessage(buffer);
					
					buffer = new byte[9];
					tmp = new byte[9];
				}
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}
		// Ignore all the other eventTypes, but you should consider the other ones.
	}
	
	public synchronized void write(byte[] b){
		System.out.println("Writing command");
    	try{
    		this.output.write(b, 0, b.length);
    	} catch(Exception e){
    		e.printStackTrace();
    	}
	}
	
	public void setListener(SerialListener listener){
		this.listener = listener;
	}
}
