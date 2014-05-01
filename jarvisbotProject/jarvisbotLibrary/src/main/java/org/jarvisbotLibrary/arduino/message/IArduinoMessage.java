package kercar.raspberry.arduino.message;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class IArduinoMessage {
	
	public static final byte ERROR = -1;
	public static final byte OK = 0;
	public static final byte STOP = 1;
	public static final byte FORWARD = 2;
	public static final byte BACKWARD = 3;
	public static final byte LEFT = 4;
	public static final byte RIGHT = 5;
	public static final byte GETBLOCK = 6;
	public static final byte GETPOS = 7;
	public static final byte GETANGLE = 8;
	public static final byte GETGPSINFO = 9;
	public static final byte RECEIVE_BLOCK = 10;
	public static final byte RECEIVE_POS = 11;
	public static final byte RECEIVE_ANGLE = 12;
	public static final byte RECEIVE_ARRIVED = 13;
	public static final byte RECEIVE_GPSINFO = 14;
	public static final byte RECEIVE_TURN = 15;
	
	//STOP Turn est en 16 coté arduino ! 
	public static final byte LEFTANGLE = 17;
	public static final byte RIGHTANGLE = 18;
	
	public static final int LENGTH = 9;

	protected byte id;
	protected byte[][] params = new byte[2][4];
	protected final int paramSize = 4;
	protected final int numParam = 2;
	
	public IArduinoMessage(byte id){
		ByteBuffer bb = ByteBuffer.allocate(1);
		bb.put(id);
		this.id = bb.array()[0];
	}
	
	public void setParam(int index, int value){
		// pre-cond
		if(index >= 2){
			System.err.println("Parameter out of bounds");
			return;
		}
		
		if(value >= Integer.MAX_VALUE){
			System.err.println("Value out of bounds");
		}
		
		this.params[index] = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(value).array();
	}
	
	public int getParam(int index){
		return toInt(params[index], 4);
	}
	
	public int getID(){
		return Integer.valueOf(id);
	}
	
	public byte[] toBytes(){
		ByteBuffer res = ByteBuffer.allocate((this.numParam*this.paramSize)+1);
		res.put(this.id).put(this.params[0]).put(this.params[1]);
		return res.array();
	}
	
	public static String toBinary( byte[] bytes )
	{
	    StringBuilder sb = new StringBuilder(bytes.length * Byte.SIZE);
	    for( int i = 0; i < Byte.SIZE * bytes.length; i++ )
	        sb.append((bytes[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
	    return sb.toString();
	}
	
	public String toString(){
		byte[] msg = this.toBytes();
		return toBinary(msg);
	}
	
	public static int extractID(byte[] b){
		return b[0];
	}
	
	public static int toInt(byte[] b, int length){
		ByteBuffer bb = ByteBuffer.wrap(b, 0, length).order(ByteOrder.LITTLE_ENDIAN);
		return bb.getInt();
	}
	
	public static IArduinoMessage fromBytes(byte[] bytes){
		IArduinoMessage msg = new IArduinoMessage(bytes[0]);
		ByteBuffer bb = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);
		msg.setParam(0, bb.getInt(1));
		msg.setParam(1, bb.getInt(5));
		return msg;
	}
}
