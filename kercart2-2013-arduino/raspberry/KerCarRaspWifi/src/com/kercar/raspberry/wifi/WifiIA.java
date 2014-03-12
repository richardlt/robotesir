package com.kercar.raspberry.wifi;
public class WifiIA extends Thread {

	private WifiManager wifiManager;
	private boolean run;
	
	
	public WifiIA(String initPath) {
		this.wifiManager = WifiManager.getInstance(initPath);
		this.run = true;
		this.start();
	}
	
	public void run() {
		while(this.run) {
			if(!this.wifiManager.isConnected()) {
				this.wifiManager.connection();
			} else {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}
	
	public void terminate(){
		this.run = false;
	}
}
