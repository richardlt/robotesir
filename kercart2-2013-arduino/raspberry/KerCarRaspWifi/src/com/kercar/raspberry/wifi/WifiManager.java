package com.kercar.raspberry.wifi;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Properties;

public class WifiManager {

	private static WifiManager instance;
	public final static int WIFSIC = 0;
	public final static int WFREE = 1;
	public final static int URENNES1 = 2;
	public final static int PHANTOM = 3;
	
	private final String WIFSIC_CONF = "/opt/apache-tomcat-7.0.35/wpa_supplicant_wifsic.conf";
	private final String WFREE_CONF = "/opt/apache-tomcat-7.0.35/wpa_supplicant_wfree.conf";
	private final String URENNES1_CONF = "/opt/apache-tomcat-7.0.35/wpa_supplicant_urennes1.conf";
	
	private static String USER = "gael.goinvic@etudiant.univ-rennes1.fr";
	private static String PWD = "NOIP-PASSWORD";
	
	private String wifiList;
	private boolean isIPNotified = false;
	
	private static String tomPath = "";
	
	
	private WifiManager() {
		loadProperties();
	}
	
	public static WifiManager getInstance(String initPath) {
		if(instance == null) {
			tomPath = initPath+"/";
			instance = new WifiManager();
		}
		return instance;
	}
				
	public void connection(){
		Log("Trying to connect");
		getSignalList();
		if(getSignalStrength(PHANTOM) > 50)
			connect(PHANTOM);
		else if(getSignalStrength(WIFSIC) > 50)
			connect(WIFSIC);
		else if(getSignalStrength(WFREE) > 50)
			connect(WFREE);
		else if(getSignalStrength(URENNES1) > 50)
			connect(URENNES1);
		else{
			System.out.println("No network found");
			return;
		}
		try {
			Thread.sleep(7500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
		
	public void connect(int network){
		stopInterface();
		wakeInterface();
		switch(network){
		case PHANTOM:
			break;
		case WIFSIC:
			configNetwork(WIFSIC_CONF);
			break;
		case WFREE:
			break;
		case URENNES1:
			break;
		default:
			break;
		}
		connectNetwork();
	}
	
	private void stopInterface(){
		Log("Stopping interface");
		ProcessBuilder pb = new ProcessBuilder("sudo", "-A", "ifconfig", "wlan0", "down");
		 Map<String, String> env = pb.environment();
		 env.put("SUDO_ASKPASS", tomPath+"set_pass.sh");
		try {
			Process p = pb.start();
			p.waitFor();
			getProcessOutput(p);
		} catch (Exception e) {
			Log("Cannot stop interface");
			Log(e.getMessage());
		}
	}
	
	private void wakeInterface(){
		Log("Waking up interface");
		ProcessBuilder pb = new ProcessBuilder("sudo", "-A", "ifconfig", "wlan0", "up");
		 Map<String, String> env = pb.environment();
		 env.put("SUDO_ASKPASS", tomPath+"set_pass.sh");
		try {
			Process p = pb.start();
			p.waitFor();
			getProcessOutput(p);
		} catch (Exception e) {
			Log("Cannot wake interface");
			Log(e.getMessage());
		}
	}
	
	private void configNetwork(String configFile){
		Log("Configuring network");
		ProcessBuilder pb = new ProcessBuilder("sudo", "-A", "wpa_supplicant", "-B", "-c", configFile, "-i", "wlan0");
		 Map<String, String> env = pb.environment();
		 env.put("SUDO_ASKPASS", tomPath+"set_pass.sh");
		try {
			Process p = pb.start();
			p.waitFor();
			getProcessOutput(p);
		} catch (Exception e) {
			Log("Can't config network");
			Log(e.getMessage());
		}
	}
	
	private void connectNetwork(){
		Log("Connexion..");
		ProcessBuilder pb = new ProcessBuilder("sudo", "-A", "dhclient", "wlan0");
		 Map<String, String> env = pb.environment();
		 env.put("SUDO_ASKPASS", tomPath+"set_pass.sh");
		try {
			Process p = pb.start();
			p.waitFor();
			getProcessOutput(p);
		} catch (Exception e) {
			Log("Can't connect");
			Log(e.getMessage());
		}
	}
		
	private String getProcessOutput(Process p){
				
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String tmp;
		StringBuffer bf = new StringBuffer();
		
		try{
			while( (tmp = br.readLine()) != null ){
				bf.append(tmp+System.getProperty("line.separator"));
			}
			br.close();
		} catch(Exception e){
			e.printStackTrace();
		}
		System.out.println(bf.toString());
		return bf.toString();
	}
	
	public void notifyIPChange(){
		String ip = null;
		Log("Getting IP");
		try{
			URL whatismyip = new URL("http://checkip.amazonaws.com");
			BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
			ip = in.readLine(); //you get the IP as a String
		} catch(Exception e){
			Log("Can't get IP on Amazon :(");
			Log(e.getMessage());
		}
		
		try{
			Log("Notifying IP");
			Authenticator.setDefault (new Authenticator() {
			    protected PasswordAuthentication getPasswordAuthentication() {
			        return new PasswordAuthentication (USER, PWD.toCharArray());
			    }
			});
			URL noip = new URL("http://dynupdate.no-ip.com/nic/update?hostname=kercar2013.no-ip.biz&myip="+ip);
			URLConnection uc = noip.openConnection();
	        BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
	        String inputLine;
	
	        while ((inputLine = in.readLine()) != null) {
	            System.out.println(inputLine);
	        }
	        in.close();
			Authenticator.setDefault(null);
		} catch(Exception e){
			Log("Can't notify IP");
			Log(e.getMessage());
		}
	}
	
	public boolean isConnected(){
		Log("Checking connection");
		Socket s = new Socket();
		try{
			s.connect(new InetSocketAddress("www.univ-rennes1.fr", 80));
			s.close();
			if(!isIPNotified){
				notifyIPChange();
				this.isIPNotified = true;
			}
			Log("Seems connected");
			return true;
		} catch(Exception e){
			Log("Seems not connected");
			Log(e.getMessage());
			this.isIPNotified = false;
			return false;
		}
	}
	
	public void getSignalList(){
		Log("Getting signals list");
		ProcessBuilder pb = new ProcessBuilder("sudo", "-A", "iwlist", "wlan0", "scan");
		Map<String, String> env = pb.environment();
		env.put("SUDO_ASKPASS", tomPath+"set_pass.sh");
		try{
			Process p = pb.start();
			p.waitFor();
			System.out.println(this.wifiList);
			this.wifiList =  getProcessOutput(p);
		}catch(Exception e){
			Log("Can't get signals list");
			Log(e.getMessage());
		}
	}

	public int getSignalStrength(int network){
		Log("Getting signal strength");
		ProcessBuilder pb = null;
		switch(network){
		case PHANTOM:
			pb = new ProcessBuilder(tomPath+"list_wifi.sh", wifiList, "Phantom");
			break;
		case WIFSIC:
			pb = new ProcessBuilder(tomPath+"list_wifi.sh", wifiList, "wifsic");
			break;
		case WFREE:
			pb = new ProcessBuilder(tomPath+"list_wifi.sh", wifiList, "wifsic-free");
			break;
		case URENNES1:
			pb = new ProcessBuilder(tomPath+"list_wifi.sh", wifiList, "Universite_Rennes1");
			break;
		default:
			break;
		}
		try{
			Process p = pb.start();
			p.waitFor();
			return Integer.valueOf(getProcessOutput(p).split("=")[1].split("/")[0]);
		} catch(Exception e){
			Log("Can't get signal strength");
			Log(e.getMessage());
			return 0;
		}
	}
	
	private void loadProperties(){
		Log("Loading credentials");
		Properties prop = new Properties();
		try{
			prop.load(new FileInputStream(tomPath+"config.properties"));
			USER = prop.getProperty("user");
			PWD = prop.getProperty("password");
		} catch(Exception e){
			Log("Can't load credentials");
			Log(e.getMessage());
		}
	}
	
	private void Log(String s){
		s = s.concat("\n");
		try{
			FileOutputStream fos = new FileOutputStream(tomPath+"logs/"+"KerCar.log", true);
			fos.write(s.getBytes());
			fos.close();
		}
		catch(Exception e){
			
		}
	}
}
