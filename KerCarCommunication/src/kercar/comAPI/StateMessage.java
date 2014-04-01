package kercar.comAPI;

/**
 * Message donnant les informations sur le robot
 * Du Raspberry vers l'Android
 */
public class StateMessage extends Message implements IStateMessage {

	public static final int INDEX_LONGITUDE = 0;
	public static final int INDEX_LATITUDE = 1;
	public static final int INDEX_ORIENTATION = 2;
	public static final int INDEX_STUCK = 3;
	public static final int INDEX_GPS = 4;
	//public static final int INDEX_TELEMETRE = 2;
	//public static final int INDEX_BATTERY = 3;
	
	public StateMessage(int longitude, int latitude, int orientation, boolean stuck, boolean GPSReady) {
		super(Message.STATE);
		
		this.params.add(INDEX_LONGITUDE, Integer.toString(longitude));
		this.params.add(INDEX_LATITUDE, Integer.toString(latitude));
		this.params.add(INDEX_ORIENTATION, Integer.toString(orientation));
		this.params.add(INDEX_STUCK, Boolean.toString(stuck));
		this.params.add(INDEX_GPS, Boolean.toString(GPSReady));
		//this.params.add(INDEX_TELEMETRE, Integer.toString(telemetre));
		//this.params.add(INDEX_BATTERY, Integer.toString(battery));
	}
	
	public StateMessage(Message m) {
		super(Message.STATE);
		this.params = m.getParams();
	}

	@Override
	public int getLongitude() {
		return Integer.parseInt(this.params.get(INDEX_LONGITUDE));
	}

	@Override
	public void setLongitude(int longitude) {
		this.params.set(INDEX_LONGITUDE, Integer.toString(longitude));
	}
	
	@Override
	public int getLatitude() {
		return Integer.parseInt(this.params.get(INDEX_LATITUDE));
	}
	
	@Override
	public void setLatitude(int latitude) {
		this.params.set(INDEX_LATITUDE, Integer.toString(latitude));
	}

	@Override
	public int getOrientation() {
		return Integer.parseInt(this.params.get(INDEX_ORIENTATION));
	}

	@Override
	public void setOrientation(int orientation) {
		this.params.set(INDEX_ORIENTATION, Integer.toString(orientation));
	}
	
	@Override
	public boolean isStuck() {
		return Boolean.parseBoolean(this.params.get(INDEX_STUCK));
	}
	
	@Override
	public void stick(boolean obstacle) {
		this.params.set(INDEX_STUCK, Boolean.toString(obstacle));
	}
	
	@Override
	public void setGPSState(boolean state){
		this.params.set(INDEX_GPS, Boolean.toString(state));
	}
	
	@Override
	public boolean getGPSState(){
		return Boolean.parseBoolean(this.params.get(INDEX_GPS));
	}

	/*
	@Override
	public int getTelemetreInfos() {
		return Integer.parseInt(this.params.get(INDEX_TELEMETRE));
	}

	@Override
	public void setTelemetreInfos(int infos) {
		this.params.set(INDEX_TELEMETRE, Integer.toString(infos));
	}

	@Override
	public int getBatteryLevel() {
		return Integer.parseInt(this.params.get(INDEX_BATTERY));
	}

	@Override
	public void setBatteryLevel(int level) {
		this.params.set(INDEX_BATTERY, Integer.toString(level));
	}
	*/

}
