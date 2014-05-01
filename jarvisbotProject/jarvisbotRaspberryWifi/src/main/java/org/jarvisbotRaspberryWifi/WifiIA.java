package org.jarvisbotRaspberryWifi;
public class WifiIA extends Thread {

	private WifiManager wifiManager;
	private boolean run;
	
	
	public WifiIA(String initPath) {
		this.wifiManager = WifiManager.getInstance(initPath);
		this.run = true;
		this.start();
		System.out.println("started");
	}
	
	public void run() {
		while(this.run) {
			if(!this.wifiManager.isConnected()) {
				System.out.println("not connected");
				this.wifiManager.connection();
			} else {
				try {
					System.out.println("going to sleep");
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
		System.out.println("terminated");
	}
}
