package com.kercar.AsyncTask;

import kercar.android.IComAndroid;
import kercar.comAPI.CMDPhotoMessage;
import android.os.AsyncTask;
import android.util.Log;

public class AsyncPrendrePhoto extends AsyncTask<Void, Void, Void> {

	//Attributs
	private IComAndroid comAndroid;
	private String msgMail;
	
	//Constructeurs
	public AsyncPrendrePhoto(String msgMail, IComAndroid comAndroid) {
		this.msgMail = msgMail;
		this.comAndroid = comAndroid;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		try {
			Log.e("AsyncPrendrePhoto", "Prendre Photo");
			CMDPhotoMessage cmdPhotoMessage = new CMDPhotoMessage(msgMail);
			this.comAndroid.envoyerMessage(cmdPhotoMessage);
			this.comAndroid.lireReponse();
		} catch (Exception e) {e.printStackTrace();}
		return null;
	}
}