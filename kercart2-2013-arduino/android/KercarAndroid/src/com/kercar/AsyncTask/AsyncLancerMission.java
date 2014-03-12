package com.kercar.AsyncTask;

import java.util.List;

import kercar.android.IComAndroid;
import kercar.comAPI.CMDMissionMessage;
import android.os.AsyncTask;
import android.util.Log;

public class AsyncLancerMission extends AsyncTask<Void, Void, Void> {
	//Attributs
	private List<Integer> coords;
	private boolean takePhoto;
	private boolean goBack;
	private String email;
	private IComAndroid comAndroid;
	
	//Constructeurs
	public AsyncLancerMission(List<Integer> coords, boolean takePhoto, boolean goBack, String email, IComAndroid comAndroid) {
		this.coords = coords;
		this.takePhoto = takePhoto;
		this.goBack = goBack;
		this.email = email;
		this.comAndroid = comAndroid;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		try {			
			Log.v("AsyncLancerMission", "Mission lancée");
			CMDMissionMessage cmdCommand = new CMDMissionMessage(coords, takePhoto, goBack, email);
			this.comAndroid.envoyerMessage(cmdCommand);
			this.comAndroid.lireReponse();
		} catch (Exception e) {e.printStackTrace();}
		
		return null;
	}
}
