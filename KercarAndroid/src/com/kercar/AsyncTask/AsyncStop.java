package com.kercar.AsyncTask;

import kercar.android.IComAndroid;
import kercar.comAPI.CMDStopMessage;
import android.os.AsyncTask;
import android.util.Log;

public class AsyncStop extends AsyncTask<Void, Void, Void> {

	//Attributs
	private IComAndroid comAndroid;
	
	//Constructeurs
	public AsyncStop(IComAndroid comAndroid) {
		this.comAndroid = comAndroid;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		try {
			CMDStopMessage cmdCommand = new CMDStopMessage();
			Log.e("AsyncStop", "Stop");
			this.comAndroid.envoyerMessage(cmdCommand);
			this.comAndroid.lireReponse();
		} catch (Exception e) {e.printStackTrace();}
		
		return null;
	}
}