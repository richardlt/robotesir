package com.kercar.AsyncTask;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import kercar.android.IComAndroid;
import kercar.comAPI.PingMessage;

public class ThreadPing extends Thread{
	
	//Attributs
	private IComAndroid comAndroid;	
	private PingMessage pingMessage;
	private Context context;
	private Activity activity;
	
	public ThreadPing(IComAndroid com, Context context, Activity activity){
		this.comAndroid = com;
		this.context = context;
		this.activity = activity;
	}
	
	@Override
	public void run() {
		while(true){
			try {
				pingMessage = new PingMessage();
				this.comAndroid.envoyerMessage(pingMessage);
				this.comAndroid.lireReponse();
			} catch (Exception e) {
				activity.runOnUiThread(new Runnable() {
				    public void run() {
				    	Log.e("coucou", "ici");
				        Toast.makeText(context, "Robot hors ligne", Toast.LENGTH_SHORT).show();
				    }
				});
			}
			try {
				sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}